package sk.gursky.nosql.crawl;

import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import sk.gursky.nosql.aislike.DaoFactory;
import sk.gursky.nosql.crawl.entity.Download;

public class DownloadTest {
	public static void main(String[] args) {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);

		DownloadDao downloadDao = DaoFactory.INSTANCE.getDownloadDao();
		List<Download> downloads = downloadDao.getAllDownloads();
		for (Download download: downloads) {
			System.out.println(download.getId() + " : " + download.getSeedPage().getUrl() + ": #pages:" + download.getPages().size());
		}
	}
}
