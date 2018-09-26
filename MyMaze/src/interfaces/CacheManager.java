package interfaces;

import java.io.IOException;

public interface CacheManager {
	void save(String problem, String solution) throws IOException;
	String load(String problem) throws IOException;
}