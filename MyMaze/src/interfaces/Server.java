package interfaces;

import java.io.IOException;

public interface Server {
	void start(ClientHandler clientHandler) throws IOException;
	void stop();
}
