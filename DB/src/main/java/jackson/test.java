package jackson;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import DAO.IDao;
import hibernate.MyDaoFactory;
import model.Resident;

public class test {

	static IDao<Resident> mydao = MyDaoFactory.getInstance().getResidentDAO();

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("filename.txt"), "utf-8"))) {
			for (Resident r : mydao.getAllItems("name", null, null, null))
				writer.write(r.toString());
			writer.close();
		}

	}

}
