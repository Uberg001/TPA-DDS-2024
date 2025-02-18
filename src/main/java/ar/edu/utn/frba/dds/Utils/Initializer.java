package ar.edu.utn.frba.dds.Utils;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class Initializer implements WithSimplePersistenceUnit {

    public static void init() {
        new Initializer()
                .iniciar();
    }

    private Initializer iniciar() {
        entityManager().getTransaction().begin();
        return this;
    }

}
