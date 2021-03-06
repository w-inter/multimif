package com.multimif.git;

/**
 * Contient les status de GIT
 */
public enum GitStatus {

    // Liste des codes de retour pour le module git
    REPOSITORY_NOT_CREATED(8400, "Repository créé"),
    REPOSITORY_CREATED(8200, "Repository créé"),
    BRANCH_NOT_CREATED(9400, "Nom de branche déjà existant"),
    BRANCH_CREATED(9200, "Branche créée"),
    COMMIT_DONE(7200, "commit éffectué"),
    COMMIT_FAILED(7400, "échec du commit"),
    CLONE_SUCCESS(6200, "Clone réussi"),
    CLONE_FAILED(6400, "échec du clonage");



    public final int value;

    private final String descr;

    GitStatus(int value, String descr) {
        this.value = value;
        this.descr = descr;
    }

    public int getValue() {
        return value;
    }

    public String getDescr() {
        return descr;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
