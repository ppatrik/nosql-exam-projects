package sk.gursky.nosql.crawl;

import java.util.List;

import sk.gursky.nosql.crawl.entity.Download;

public interface DownloadDao {

	List<Download> getAllDownloads();

	Download getDownloadById(long downloadId);

}