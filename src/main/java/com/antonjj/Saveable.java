package com.antonjj;

import java.io.IOException;

public interface Saveable<T> {
	void save(T data) throws IOException;

}
