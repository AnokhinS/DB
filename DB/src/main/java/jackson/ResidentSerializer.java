package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import model.Resident;

public class ResidentSerializer extends JsonSerializer<Resident> {

	@Override
	public void serialize(Resident obj, JsonGenerator jGen, SerializerProvider arg2) throws IOException {
		jGen.writeStartObject();
		jGen.writeNumberField("id", obj.getId());
		jGen.writeRaw('\n');
		jGen.writeStringField("Имя", obj.getName());
		jGen.writeRaw('\n');
		jGen.writeStringField("Пол", obj.getSex());
		jGen.writeRaw('\n');
		jGen.writeStringField("Телефон", obj.getPhone());
		jGen.writeRaw('\n');
		jGen.writeStringField("Тип проживающего", obj.getResType().getName());
		jGen.writeRaw('\n');
		jGen.writeStringField("Форма обучения", obj.getFoe().getName());
		jGen.writeRaw('\n');
		jGen.writeStringField("Факультет", obj.getFaculty().getName());
		jGen.writeRaw('\n');
		jGen.writeStringField("Адрес общежития", obj.getRoom().getStudentHouse().getAddress());
		jGen.writeRaw('\n');
		jGen.writeStringField("Комната", obj.getRoom().getName());
		jGen.writeRaw('\n');

		jGen.writeEndObject();
	}

}