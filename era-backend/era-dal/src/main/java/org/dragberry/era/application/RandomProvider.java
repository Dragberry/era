package org.dragberry.era.application;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomProvider {

	private static final Random RANDOM = new Random();
	
	private static final int CURRENT_YEAR = LocalDate.now().getYear();
	
	private static final List<String> NAMES = Arrays.asList(
			"Андрей", "Борис", "Виталий", "Денис", "Евгений", "Иван", "Леонид", "Максим", "Петр", "Сергей");
	
	private static final List<String> SURNAMES = Arrays.asList(
			"Алевтеев", "Бодунов", "Веллер", "Драгун", "Евтушенко", "Железный", "Леонтьев", "Николаев", "Самсонов", "Шилов");

	private static final List<String> MIDDLE_NAMES = Arrays.asList(
			"Андревич", "Борисович", "Витальевич", "Денисович", "Евгеньевич", "Иванович", "Леонидович", "Максимович", "Петрович", "Сергеевич");

	private static final List<String> RUVD = Arrays.asList(
			"Ленинское РУВД г.Минска", "Советское РУВД г.Минска", "Октябрьское РУВД г.Минска", "Московское РУВД г.Минска");
	
	private static final List<String> CITIES = Arrays.asList(
			"Минск", "Брест", "Могилев", "Борисов", "Жодино", "Барановичи", "Клецк", "Гродно", "Сморгонь");
	
	private static final List<String> STREETS = Arrays.asList(
			"Прушинских", "Уборевича", "Московская", "Независимости", "Куйбышева", "Красная", "Хоружей", "Голодеда", "Янки Лучины");
	
	
	public static String getNumber() {
		return String.valueOf(RANDOM.nextInt(100));
	}
	
	public static String getStreet() {
		return STREETS.get(RANDOM.nextInt(STREETS.size()));
	}
	
	public static String getCity() {
		return CITIES.get(RANDOM.nextInt(CITIES.size()));
	}
	
	public static String getRUVD() {
		return RUVD.get(RANDOM.nextInt(RUVD.size()));
	}
	
	public static String getName() {
		return NAMES.get(RANDOM.nextInt(NAMES.size()));
	}
	
	public static String getSurname() {
		return SURNAMES.get(RANDOM.nextInt(SURNAMES.size()));
	}
	
	public static String getMiddleName() {
		return MIDDLE_NAMES.get(RANDOM.nextInt(MIDDLE_NAMES.size()));
	}
	
	public static LocalDate getBirthDate() {
		return LocalDate.of(CURRENT_YEAR - 15, Month.of(RANDOM.nextInt(12)), RANDOM.nextInt(28));
	}
	
	public static LocalDate getDate() {
		return LocalDate.of(CURRENT_YEAR - 2, Month.of(RANDOM.nextInt(12)), RANDOM.nextInt(28));
	}

	public static StringBuffer getStringBuffer(String start, int length) {
		return getStringBuffer("", length);
	}
	
	public static StringBuffer getStringBuffer(int length) {
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i < length; i++) {
			sb.append(RANDOM.nextInt(10));
		}
		return sb;
	}
	
	public static String getString(String start,int length) {
		return getStringBuffer(start, length).toString();
	}
	
	public static String getString(int length) {
		return getStringBuffer(length).toString();
	}
	
	public static String getPhone() {
		return getString("37529", 7);
	}

	public static String getMail() {
		return getStringBuffer("m", 7).append("@mail.com").toString();
	}

}
