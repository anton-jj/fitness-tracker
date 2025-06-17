package com.antonjj;

import java.io.IOException;
import java.util.List;

public interface Loadable<T> {
	List<T> load() throws IOException;

}
