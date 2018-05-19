package hibernate;

import DAO.DaoImpls;
import DAO.IDao;
import model.Faculty;
import model.FormOfEducation;
import model.Payment;
import model.Personal;
import model.Profession;
import model.Resident;
import model.ResidentType;
import model.Room;
import model.StudentHouse;

public class Factory {
	private static Factory instance = null;
	private static IDao<Faculty> facultyDAO = null;
	private static IDao<FormOfEducation> foeDAO = null;
	private static IDao<Profession> profDAO = null;
	private static IDao<ResidentType> resTypeDAO = null;
	private static IDao<Room> roomDAO = null;
	private static IDao<StudentHouse> studHouseDAO = null;
	private static IDao<Personal> personalDAO = null;
	private static IDao<Payment> paymentDAO = null;
	private static IDao<Resident> residentDAO = null;

	public static synchronized Factory getInstance() {
		if (instance == null) {
			instance = new Factory();
		}
		return instance;
	}

	public IDao<Faculty> getFacultyDAO() {
		if (facultyDAO == null) {
			facultyDAO = new DaoImpls<Faculty>(Faculty.class);
		}
		return facultyDAO;
	}

	public IDao<FormOfEducation> getFormOfEducationDAO() {
		if (foeDAO == null) {
			foeDAO = new DaoImpls<FormOfEducation>(FormOfEducation.class);
		}
		return foeDAO;
	}

	public IDao<Profession> getProfessionDAO() {
		if (profDAO == null) {
			profDAO = new DaoImpls<Profession>(Profession.class);
		}
		return profDAO;
	}

	public IDao<ResidentType> getResidentTypeDAO() {
		if (resTypeDAO == null) {
			resTypeDAO = new DaoImpls<ResidentType>(ResidentType.class);
		}
		return resTypeDAO;
	}

	public IDao<Room> getRoomDAO() {
		if (roomDAO == null) {
			roomDAO = new DaoImpls<Room>(Room.class);
		}
		return roomDAO;
	}

	public IDao<StudentHouse> getStudentHouseDAO() {
		if (studHouseDAO == null) {
			studHouseDAO = new DaoImpls<StudentHouse>(StudentHouse.class);
		}
		return studHouseDAO;
	}

	public IDao<Personal> getPersonalDAO() {
		if (personalDAO == null) {
			personalDAO = new DaoImpls<Personal>(Personal.class);
		}
		return personalDAO;
	}

	public IDao<Payment> getPaymentDAO() {
		if (paymentDAO == null) {
			paymentDAO = new DaoImpls<Payment>(Payment.class);
		}
		return paymentDAO;
	}

	public IDao<Resident> getResidentDAO() {
		if (residentDAO == null) {
			residentDAO = new DaoImpls<Resident>(Resident.class);
		}
		return residentDAO;
	}

}