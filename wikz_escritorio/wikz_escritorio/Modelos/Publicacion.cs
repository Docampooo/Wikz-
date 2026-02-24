using System;
using Newtonsoft.Json;

namespace wikz_escritorio.Modelos
{
    public class Publicacion
    {
        [JsonProperty("id")]
        public int Id { get; set; }

        [JsonProperty("idUsuario")]
        public int IdUsuario { get; set; }

        [JsonProperty("titulo")]
        public string Titulo { get; set; }

        [JsonProperty("descripcion")]
        public string Descripcion { get; set; }

        [JsonProperty("imagenBase64")]
        public string ImagenBase64 { get; set; }

        [JsonProperty("fechaCreacion")]
        public string FechaCreacion { get; set; }

        public Publicacion()
        {
            Titulo = "";
            Descripcion = "";
            FechaCreacion = DateTime.Now.ToString();
        }

        public override string ToString()
        {
            return $"{Titulo} (ID: {Id})";
        }
    }
}