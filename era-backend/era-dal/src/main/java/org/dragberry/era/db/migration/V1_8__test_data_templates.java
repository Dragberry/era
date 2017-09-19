package org.dragberry.era.db.migration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import org.apache.commons.io.IOUtils;
import org.dragberry.era.domain.ReportTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

import com.googlecode.flyway.core.api.migration.spring.SpringJdbcMigration;

public class V1_8__test_data_templates implements SpringJdbcMigration  {

	private static final String SELECT_REPORT_TEMPLATE_GEN_VALUE = "SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'REPORT_TEMPLATE_GEN';";
	private static final String SELECT_INSTITUTION_KEY = "SELECT EDUCATION_INSTITUTION_KEY FROM EDUCATION_INSTITUTION WHERE NAME = 'Филиал БГУИР \"Минский радиотехнический колледж\"';";
	private static final String UPDATE_REPORT_TEMPLATE_GEN_VALUE = "UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'REPORT_TEMPLATE_GEN'";
	private static final String INSERT_REPORT_TEMPLATE = "INSERT INTO REPORT_TEMPLATE (REPORT_TEMPLATE_KEY, EDUCATION_INSTITUTION_KEY, FILE_EXTENSION, TITLE, TEMPLATE) VALUES (?, ?, ?, ?, ?);";

	private LobHandler handler = new DefaultLobHandler();
	
	@Override
	public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
		Long reportKey = jdbcTemplate.queryForObject(SELECT_REPORT_TEMPLATE_GEN_VALUE, Long.class);
		Long institutionKey = jdbcTemplate.queryForObject(SELECT_INSTITUTION_KEY, Long.class);
		
		PreparedStatement prTemplateStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(INSERT_REPORT_TEMPLATE);
		prTemplateStatement.setLong(1, reportKey);
		prTemplateStatement.setLong(2, institutionKey);
		prTemplateStatement.setString(3, ReportTemplate.Type.DOCX.fileExtension);
		prTemplateStatement.setString(4, "МРК (платное)");
		LobCreator lobCreator = handler.getLobCreator();
        InputStream input = null;
        try {
            input = getClass().getResourceAsStream("/db/migration/V1/mrc-paid-contract.docx");
            byte[] data = null;
            if (input != null) {
                data = IOUtils.toByteArray(input);
            }
            lobCreator.setBlobAsBytes(prTemplateStatement, 5, data);
        } catch (IOException e) {
        	prTemplateStatement.setBytes(5, null);
        } finally {
            IOUtils.closeQuietly(input);
        }
		prTemplateStatement.execute();
        lobCreator.close();
        
        jdbcTemplate.execute(UPDATE_REPORT_TEMPLATE_GEN_VALUE);
	}


}
