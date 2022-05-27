package org.example.bridge.properties;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:uipath.yml")
public class BridgeProperties {
    public static final String TENANT_NAME_PROPERTY = "X-UIPATH-TenantName";
    public static final String ORGUNIT_ID_PROPERTY = "X-UIPATH-OrganizationUnitId";
    public static final String FOLDER_PATH_PROPERTY = "X-UIPATH-FolderPath";
    @Value("${account-name}")
    protected String accountName;
    @Value("${tenant-name}")
    protected String tenantName;
    @Value("${url}")
    protected String url;
    @Value("${folder-path}")
    protected String folderPath;
    @Value("${organization-unit-id}")
    protected Integer organizationUnitId;
    @Value("${auth-url}")
    protected String authUrl;
    @Value("${user}")
    protected String user;
    @Value("${key}")
    protected String key;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTenantName() {
        return tenantName;
    }
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getFolderPath() {
        return folderPath;
    }
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public Integer getOrganizationUnitId() {
        return organizationUnitId;
    }
    public void setOrganizationUnitId(Integer organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
    }

    public String getApiUrl(){
        return this.url + "/" + this.getAccountName() + "/" + this.getTenantName() + "/odata/";
    }

    public String getAuthUrl() {
        return authUrl;
    }
    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public Header getTenantHeader() {
        return new BasicHeader(TENANT_NAME_PROPERTY, this.getTenantName());
    }

    public Header getOrgUnitIdHeader() {
        return new BasicHeader(ORGUNIT_ID_PROPERTY, this.getOrganizationUnitId().toString());
    }

    public Header getFolderPathHeader() {
        return new BasicHeader(FOLDER_PATH_PROPERTY, this.getFolderPath());
    }

    public Header getContentTypeHeader() {
        return new BasicHeader("Content-Type", "application/json");
    }

    public String toString() {
        return "BridgeProperties [accountName=" + this.accountName + ", tenantName=" + this.tenantName + ", url=" + this.url + ", authentication=" + "(" + this.authUrl + ", " + this.user + ", " + this.key + ")" + "]";
    }

    public BridgeProperties(){

    }

}
