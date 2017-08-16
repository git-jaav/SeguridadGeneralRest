package pe.jaav.sistemas.SeguridadGeneralRest.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties("spring.jpa.properties.hibernate")
public class HibernateConfigProperties {

    @Valid
    @NotNull
    private String dialect;
    
    @Valid
    @NotNull
    private String show_sql;
    
    @Valid
    @NotNull
    private String format_sql;
    
    @Valid
    @NotNull
    private String  packagesToScan;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getShow_sql() {
		return show_sql;
	}

	public void setShow_sql(String show_sql) {
		this.show_sql = show_sql;
	}

	public String getFormat_sql() {
		return format_sql;
	}

	public void setFormat_sql(String format_sql) {
		this.format_sql = format_sql;
	}

	public String getPackagesToScan() {
		return packagesToScan;
	}

	public void setPackagesToScan(String packagesToScan) {
		this.packagesToScan = packagesToScan;
	}
    
    

}
