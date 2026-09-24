package com.tatf.tests;

import com.tatf.core.browser.BrowserFactory;
import com.tatf.core.browser.IBrowser;
import com.tatf.core.verification.IVerify;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminCESTest {

    private IBrowser browser;


    // =========================================================
    // SE EJECUTA ANTES DE CADA TEST
    // =========================================================

    @BeforeEach
    void beforeEach() {

        browser = BrowserFactory.getBrowser(true);

        // Entrar a AdminCES
        browser.interaction()
                .navigateTo("http://cestore.ces.com.uy/adminces/");

        // Ingresar hash
        browser.find()
                .id("pass")
                .write("3)ea60e0be3ba12c6ecd%7297868%5c4");

        // Presionar Ingresar
        browser.find()
                .css("#loginForm button[type='submit']")
                .click();

        // Esperar pantalla principal
        browser.wait("Registrarse").link();
    }


    // =========================================================
    // TEST 1 - CREAR CUENTA ADMINISTRADOR
    // =========================================================

    @Test
    void crearCuentaAdministrador() {

        browser.find()
                .link("Registrarse")
                .click();

        browser.wait("btnRegister").id();

        browser.find()
                .css("input[name='inputFirstName']")
                .write("Cynthia");

        browser.find()
                .css("input[name='inputLastName']")
                .write("Prueba");

        browser.find()
                .css("input[name='inputEmail']")
                .write("cynthia.prueba@gmail.com");

        browser.find()
                .css("input[name='inputPassword']")
                .write("12345");

        browser.find()
                .css("input[name='inputRepeatPassword']")
                .write("12345");

        browser.find()
                .css("input[name='inputCountry']")
                .write("Uruguay");

        browser.find()
                .id("btnRegister")
                .click();

        browser.wait("swal2-html-container").id();

        String mensajeObtenido = browser.find()
                .id("swal2-html-container")
                .getText();

        IVerify.create().verify(
                "Usuario creado.",
                mensajeObtenido,
                "No se mostró el mensaje de usuario creado."
        );
    }


    // =========================================================
    // TEST 2 - REINICIAR CONTRASEÑA
    // =========================================================

    @Test
    void reiniciarContrasena() {

        browser.find()
                .link("Reiniciar contraseña")
                .click();

        browser.wait("btnReset").id();

        browser.find()
                .css("input[name='inputEmail']")
                .write("yaniscorrea@gmail.com");

        browser.find()
                .css("input[name='inputPassword']")
                .write("12345");

        browser.find()
                .css("input[name='inputRepeatPassword']")
                .write("12345");

        browser.find()
                .id("btnReset")
                .click();

        browser.wait("swal2-html-container").id();

        String mensajeObtenido = browser.find()
                .id("swal2-html-container")
                .getText();

        IVerify.create().verify(
                "Contraseña reiniciada.",
                mensajeObtenido,
                "No se mostró el mensaje de contraseña reiniciada."
        );
    }


    // =========================================================
    // TEST 3 - CREAR CUENTA TESTER
    // =========================================================

    @Test
    void crearCuentaTester() {

        iniciarSesionAdministrador();

        browser.find()
                .link("Crear usuario")
                .click();

        browser.wait("btnRegister").id();

        browser.find()
                .css("input[name='inputFirstName']")
                .write("Cynthia");

        browser.find()
                .css("input[name='inputLastName']")
                .write("Tester");

        browser.find()
                .css("input[name='inputEmail']")
                .write("cynthia.tester.ces@gmail.com");

        browser.find()
                .css("select[name='inputCountry']")
                .selectValue("Uruguay");

        browser.find()
                .css("input[name='inputPassword']")
                .write("12345");

        browser.find()
                .id("testerJunior")
                .click();

        browser.find()
                .id("btnRegister")
                .click();

        browser.wait("swal2-html-container").id();

        String mensajeObtenido = browser.find()
                .id("swal2-html-container")
                .getText();

        IVerify.create().verify(
                "Usuario creado.",
                mensajeObtenido,
                "No se mostró el mensaje de usuario creado."
        );
    }


    // =========================================================
    // TEST 4 - ELIMINAR CUENTA TESTER
    // =========================================================

    @Test
    void eliminarCuentaTester() {

        String correoTester = "cynthia.eliminar.ces@gmail.com";

        // Login administrador
        iniciarSesionAdministrador();

        // Crear usuario dentro de este mismo test
        crearTesterParaEliminar(correoTester);

        // Ir a Ver usuarios
        browser.find()
                .link("Ver usuarios")
                .click();

        browser.wait("dataTable").id();

        // Ubicar usuario
        ubicarUsuarioPorCorreo(correoTester);

        // Botón eliminar de la fila correspondiente
        browser.find()
                .xpath(
                        "//tr[td[contains(text(),'" +
                                correoTester +
                                "')]]//button"
                )
                .click();

        browser.wait("swal2-html-container").id();

        String preguntaObtenida = browser.find()
                .id("swal2-html-container")
                .getText();

        IVerify.create().verify(
                "¿Eliminar usuario: " + correoTester + "?",
                preguntaObtenida,
                "No se mostró la confirmación para eliminar el usuario esperado."
        );

        // Confirmar
        browser.find()
                .css("button.swal2-confirm")
                .click();

        browser.wait(
                "//div[@id='swal2-html-container' " +
                        "and contains(text(),'Usuario eliminado.')]"
        ).xpath();

        String mensajeObtenido = browser.find()
                .id("swal2-html-container")
                .getText();

        IVerify.create().verify(
                "Usuario eliminado.",
                mensajeObtenido,
                "No se mostró el mensaje de usuario eliminado."
        );
    }


    // =========================================================
    // FUNCIÓN AUXILIAR - INICIAR SESIÓN ADMINISTRADOR
    // =========================================================

    private void iniciarSesionAdministrador() {

        browser.interaction()
                .navigateTo("http://cestore.ces.com.uy/adminces/");

        browser.find()
                .link("Iniciar sesión")
                .click();

        browser.wait("formLogin").id();

        browser.find()
                .css("input[name='inputEmail']")
                .write("yaniscorrea@gmail.com");

        browser.find()
                .css("input[name='inputPassword']")
                .write("12345");

        browser.find()
                .css("#formLogin button[type='button']")
                .click();

        browser.wait("swal2-html-container").id();

        String mensajeObtenido = browser.find()
                .id("swal2-html-container")
                .getText();

        IVerify.create().verify(
                "Sesión iniciada.",
                mensajeObtenido,
                "No se inició la sesión correctamente."
        );

        browser.find()
                .css("button.swal2-confirm")
                .click();

        browser.wait("Crear usuario").link();
    }


    // =========================================================
    // FUNCIÓN AUXILIAR - CREAR TESTER PARA ELIMINAR
    // =========================================================

    private void crearTesterParaEliminar(String correo) {

        browser.find()
                .link("Crear usuario")
                .click();

        browser.wait("btnRegister").id();

        browser.find()
                .css("input[name='inputFirstName']")
                .write("Cynthia");

        browser.find()
                .css("input[name='inputLastName']")
                .write("Eliminar");

        browser.find()
                .css("input[name='inputEmail']")
                .write(correo);

        browser.find()
                .css("select[name='inputCountry']")
                .selectValue("Uruguay");

        browser.find()
                .css("input[name='inputPassword']")
                .write("12345");

        browser.find()
                .id("testerJunior")
                .click();

        browser.find()
                .id("btnRegister")
                .click();

        browser.wait("swal2-html-container").id();

        String mensajeObtenido = browser.find()
                .id("swal2-html-container")
                .getText();

        IVerify.create().verify(
                "Usuario creado.",
                mensajeObtenido,
                "No se pudo crear el Tester que se utilizará para eliminar."
        );

        browser.find()
                .css("button.swal2-confirm")
                .click();

        browser.wait("Ver usuarios").link();
    }


    // =========================================================
    // FUNCIÓN AUXILIAR - UBICAR USUARIO POR CORREO
    // =========================================================

    private void ubicarUsuarioPorCorreo(String correo) {

        browser.wait(
                "//tr[td[contains(text(),'" + correo + "')]]"
        ).xpath();
    }


    // =========================================================
    // SE EJECUTA DESPUÉS DE CADA TEST
    // =========================================================

    @AfterEach
    void afterEach() {

        BrowserFactory.quitBrowser();
    }
}