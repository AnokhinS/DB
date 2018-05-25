package jackson;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

import model.Resident;

public class MyModule extends SimpleModule {
	private static final String NAME = "CustomModule";
	private static final VersionUtil VERSION_UTIL = new VersionUtil() {
	};

	public MyModule() {
		super(NAME, VERSION_UTIL.version());
		addSerializer(Resident.class, new ResidentSerializer());
	}
}