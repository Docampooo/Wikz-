using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using wikz_escritorio.Modelos;
namespace Wikz.Services
{

    public class Api
    {
        private static readonly HttpClient client = new HttpClient();

        // Configuración de red
        private const string BASE_URL = "http://localhost:8080/api/wikz/operaciones/";

        #region --- Helpers de Imágenes ---

        public string ImageToBase64(Image image)
        {
            if (image == null) return null;
            using (MemoryStream ms = new MemoryStream())
            {
                image.Save(ms, System.Drawing.Imaging.ImageFormat.Jpeg);
                return Convert.ToBase64String(ms.ToArray());
            }
        }

        public Image Base64ToImage(string base64)
        {
            if (string.IsNullOrEmpty(base64)) return null;
            byte[] bytes = Convert.FromBase64String(base64);
            using (MemoryStream ms = new MemoryStream(bytes))
            {
                return Image.FromStream(ms);
            }
        }

        #endregion

        #region --- Métodos de Usuario ---

        public async Task<Usuario> GetUsuarioNombrePassAsync(string nombre, string pass)
        {
            try
            {
                string nombreCod = Uri.EscapeDataString(nombre);
                string passCod = Uri.EscapeDataString(pass);

                string url = $"{BASE_URL}getUsuarioNombrePass?nombreUs={nombreCod}&passUs={passCod}";

                var response = await client.GetAsync(url);

                string contenido = await response.Content.ReadAsStringAsync();

                if (response.IsSuccessStatusCode)
                {
                    try
                    {
                        Usuario u = JsonConvert.DeserializeObject<Usuario>(contenido);

                        if (u == null)
                        {
                            MessageBox.Show("La deserialización devolvió null");
                        }

                        return u;
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("Error deserializando JSON:\n" + ex.Message);
                        return null;
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Excepción al llamar a la API:\n" + ex.Message);
            }

            return null;
        }

        public async Task<Usuario> GetUsuarioByIdAsync(int idUsuario)
        {
            try
            {
                string url = $"{BASE_URL}getUsuarioId?id={idUsuario}";

                var response = await client.GetAsync(url);

                if (response.IsSuccessStatusCode)
                {
                    string json = await response.Content.ReadAsStringAsync();
                    return JsonConvert.DeserializeObject<Usuario>(json);
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine("Error obteniendo usuario: " + ex.Message);
            }

            return null;
        }

        public async Task<bool> AddUsuarioAsync(string nombre, string email, string pass, string bio)
        {
            try
            {
                var datos = new
                {
                    nombre = nombre,
                    email = email,
                    pass = pass,
                    biografia = bio,
                    fotoPerfilBase64 = ""
                };

                string json = JsonConvert.SerializeObject(datos);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                var response = await client.PostAsync(BASE_URL + "addUsuario", content);

                // Debug para ver qué dice el servidor si falla
                if (!response.IsSuccessStatusCode)
                {
                    string errorServer = await response.Content.ReadAsStringAsync();
                    System.Diagnostics.Debug.WriteLine($"Error API: {errorServer}");
                }

                return response.IsSuccessStatusCode;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine("Excepción: " + ex.Message);
                return false;
            }
        }

        public async Task<Image> GetFotoPerfilAsync(int idUsuario)
        {
            try
            {
                var response = await client.GetAsync($"{BASE_URL}fotoPerfil?id={idUsuario}");
                if (response.IsSuccessStatusCode)
                {
                    var stream = await response.Content.ReadAsStreamAsync();
                    return Image.FromStream(stream);
                }
            }
            catch (Exception) { }
            return null;
        }

        public async Task<bool> UpdateUsuarioAsync(Usuario u, Image fotoPerfil)
        {
            try
            {
                var datos = new
                {
                    id = u.Id,
                    nombre = u.Nombre,
                    biografia = u.Biografia,
                    fotoPerfilBase64 = ImageToBase64(fotoPerfil)
                };
                string json = JsonConvert.SerializeObject(datos);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                var response = await client.PostAsync(BASE_URL + "updateUsuario", content);
                return response.IsSuccessStatusCode;
            }
            catch (Exception) { return false; }
        }

        #endregion

        #region --- Métodos de Publicaciones ---

        public async Task<List<Publicacion>> GetPublicacionesAsync()
        {
            try
            {
                var response = await client.GetAsync(BASE_URL + "getPublicaciones");
                string json = await response.Content.ReadAsStringAsync();

                MessageBox.Show("JSON RECIBIDO:\n" + json, "Depuración de Publicaciones");

                if (response.IsSuccessStatusCode)
                {
                    return JsonConvert.DeserializeObject<List<Publicacion>>(json);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error en la API: " + ex.Message);
            }
            return new List<Publicacion>();
        }

        public async Task<bool> AddPublicacionAsync(int idUsuario, string titulo, Image imagen, string descripcion)
        {
            try
            {
                var datos = new
                {
                    idUsuario = idUsuario,
                    titulo = titulo,
                    imagenBase64 = ImageToBase64(imagen),
                    descripcion = descripcion
                };
                string json = JsonConvert.SerializeObject(datos);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                var response = await client.PostAsync(BASE_URL + "addPublicacion", content);
                return response.IsSuccessStatusCode;
            }
            catch (Exception) { return false; }
        }

        public async Task<bool> EliminarPublicacionAsync(int idPublicacion)
        {
            try
            {
                var response = await client.DeleteAsync($"{BASE_URL}eliminarPublicacion?idPublicacion={idPublicacion}");
                return response.IsSuccessStatusCode;
            }
            catch (Exception) { return false; }
        }

        public async Task<Image> GetFotoPublicacionAsync(int idPublicacion)
        {
            try
            {
                string url = $"{BASE_URL}getImagenPublicacion?id={idPublicacion}";

                var response = await client.GetAsync(url);

                if (response.IsSuccessStatusCode)
                {
                    using (var stream = await response.Content.ReadAsStreamAsync())
                    {
                        return Image.FromStream(stream);
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error al descargar imagen de publicación: " + ex.Message);
            }

            return null;
        }

        public async Task<List<Publicacion>> GetPublicacionesUsuarioAsync(int idUsuario)
        {
            try
            {
                var response = await client.GetAsync(BASE_URL + "getPublicacionesUsuario?idUsuario=" + idUsuario);

                if (response.IsSuccessStatusCode)
                {
                    string json = await response.Content.ReadAsStringAsync();
                    return JsonConvert.DeserializeObject<List<Publicacion>>(json);
                }
                else if (response.StatusCode == System.Net.HttpStatusCode.NotFound)
                {
                    return new List<Publicacion>();
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine("Error al obtener publicaciones del perfil: " + ex.Message);
            }

            return new List<Publicacion>();
        }

        public async Task<string> PingAsync()
        {
            try
            {
                var response = await client.GetAsync(BASE_URL + "ping");

                string texto = await response.Content.ReadAsStringAsync();

                return $"{response.StatusCode} - {texto}";
            }
            catch (Exception ex)
            {
                return "ERROR: " + ex.Message;
            }
        }

        #endregion
    }
}