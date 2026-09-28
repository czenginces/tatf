package com.tatf.adminces.modules.adminces.test;

import com.tatf.adminces.modules.adminces.data.AdminCESData;
import com.tatf.adminces.modules.adminces.task.AdminCESTask;
import com.tatf.adminces.modules.base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminCESTest extends BaseTest {

    private AdminCESTask adminCES;

    @BeforeEach
    public void configurar() {
        this.adminCES = new AdminCESTask(browser);

        this.adminCES.enterAdminCES(
                AdminCESData.URL,
                AdminCESData.HASH
        );
    }

    @Test
    public void crearCuentaAdministrador() {

        this.adminCES.createAdministrator(
                AdminCESData.ADMIN_FIRST_NAME,
                AdminCESData.ADMIN_LAST_NAME,
                AdminCESData.ADMIN_EMAIL,
                AdminCESData.PASSWORD,
                AdminCESData.COUNTRY,
                AdminCESData.MESSAGE_USER_CREATED
        );
    }

    @Test
    public void reiniciarContrasena() {

        this.adminCES.resetPassword(
                AdminCESData.ADMIN_LOGIN_EMAIL,
                AdminCESData.PASSWORD,
                AdminCESData.MESSAGE_PASSWORD_RESET
        );
    }

    @Test
    public void crearCuentaTester() {

        this.adminCES.loginAdministrator(
                AdminCESData.ADMIN_LOGIN_EMAIL,
                AdminCESData.PASSWORD,
                AdminCESData.MESSAGE_LOGIN
        );

        this.adminCES.createTester(
                AdminCESData.TESTER_FIRST_NAME,
                AdminCESData.TESTER_LAST_NAME,
                AdminCESData.TESTER_EMAIL,
                AdminCESData.PASSWORD,
                AdminCESData.COUNTRY,
                AdminCESData.MESSAGE_USER_CREATED
        );
    }

    @Test
    public void eliminarCuentaTester() {

        this.adminCES.loginAdministrator(
                AdminCESData.ADMIN_LOGIN_EMAIL,
                AdminCESData.PASSWORD,
                AdminCESData.MESSAGE_LOGIN
        );

        this.adminCES.deleteTester(
                AdminCESData.TESTER_FIRST_NAME,
                AdminCESData.TESTER_DELETE_LAST_NAME,
                AdminCESData.TESTER_DELETE_EMAIL,
                AdminCESData.PASSWORD,
                AdminCESData.COUNTRY,
                AdminCESData.MESSAGE_USER_CREATED,
                AdminCESData.MESSAGE_DELETE_QUESTION,
                AdminCESData.MESSAGE_USER_DELETED
        );
    }
}