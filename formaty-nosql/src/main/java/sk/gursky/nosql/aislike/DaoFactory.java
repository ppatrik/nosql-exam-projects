package sk.gursky.nosql.aislike;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

import sk.gursky.nosql.crawl.DownloadDao;
import sk.gursky.nosql.crawl.MysqlDownloadDao;
import sk.gursky.nosql.crawl.entity.Download;

public enum DaoFactory {
	INSTANCE;
	
	private JdbcTemplate jdbcTemplate;
	private StudentDao studentDao;
	private DownloadDao downloadDao;
	
	public synchronized JdbcTemplate getJDBCTemplate2() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setDatabaseName("ais-like");
			dataSource.setUser("ais-like-user");
			dataSource.setPassword("iceIceBaby");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}

	public synchronized JdbcTemplate getJDBCTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName("nosql.gursky.sk");
			dataSource.setDatabaseName("ais-like");
			dataSource.setUser("student");
			dataSource.setPassword("nosql");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}

	
	public synchronized StudentDao getStudentDao() {
		if (studentDao == null) {
			studentDao = new MysqlStudentDao(getJDBCTemplate());
		}
		return studentDao;
	}
	
	public synchronized DownloadDao getDownloadDao() {
		if (downloadDao == null) {
			downloadDao = new MysqlDownloadDao(getJDBCTemplate());
		}
		return downloadDao;
	}
}
