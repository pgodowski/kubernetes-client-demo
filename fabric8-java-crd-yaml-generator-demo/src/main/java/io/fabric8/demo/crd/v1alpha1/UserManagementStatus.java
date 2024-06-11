package io.fabric8.demo.crd.v1alpha1;

public class UserManagementStatus {
    private boolean issued;
    private String issuedto;

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public String getIssuedto() {
        return issuedto;
    }

    public void setIssuedto(String issuedto) {
        this.issuedto = issuedto;
    }

}
