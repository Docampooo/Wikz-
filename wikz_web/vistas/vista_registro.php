<form method="POST" action="index.php?c=Registro&m=registrar">

    <input type="text" name="nombre" placeholder="Nombre de usuario" required>

    <input type="email" name="email" placeholder="Correo electrónico" required>

    <input type="password" name="pass" placeholder="Contraseña" required>

    <input type="password" name="pass_repeat" placeholder="Repetir contraseña" required>

    <?php if (isset($error)): ?>
        <p class="error"><?= htmlspecialchars($error) ?></p>
    <?php endif; ?>

    <button type="submit">Sign Up</button>
</form>