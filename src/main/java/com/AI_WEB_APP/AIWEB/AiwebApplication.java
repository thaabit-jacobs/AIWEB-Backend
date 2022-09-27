package com.AI_WEB_APP.AIWEB;

import com.AI_WEB_APP.AIWEB.model.ResearchArticles;
import com.AI_WEB_APP.AIWEB.model.Researcher;
import com.AI_WEB_APP.AIWEB.service.ResearchArticlesService;
import com.AI_WEB_APP.AIWEB.service.ResearcherService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;

@SpringBootApplication
public class AiwebApplication implements CommandLineRunner {
	private ResearcherService researcherService;
	private ResearchArticlesService researchArticlesService;

	public AiwebApplication(ResearcherService r, ResearchArticlesService rS) {
		this.researcherService = r;
		this.researchArticlesService = rS;
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AiwebApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		//set the default directory from the current files
		String userDirectory = FileSystems.getDefault()
				.getPath("")
				.toAbsolutePath()
				.toString();

		//default file parameters, will be replaced if user decides to update the file
		String userDay = "29";
		String userMonth = "July";
		String userYear = "2022";

		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.println("Do you want to use the DEFAULT data or UPDATE the file? \nSelect D for DEFAULT and U for Update: ");
			String userInput = input.nextLine().toLowerCase(Locale.ROOT); //get user input

			//if user input is the default file, we will then
			if (userInput.equals("d")) {
				System.out.println("Default file will be used");
				break; //break out of this loop and read from the default loop
			}
			//if the user wants to update the file
			else if (userInput.equals("u")) {
				while (true) {
					//prompt user to input the day, month and year of the file
					System.out.println("Enter the day of file: "); //prompt user to input the date
					String userDayInput = input.nextLine();

					System.out.println("Enter the month of the file: ");
					String userMonthInput = input.nextLine();

					System.out.println("Enter the year of the file: ");
					String userYearInput = input.nextLine();

					//create a temporary file
					File tempFile = new File(userDirectory
							+ "\\src\\main\\resources\\Current-Rated-Researchers-" + userDayInput + "-" + userMonthInput + "-" + userYearInput + ".xlsx");

					boolean exists = tempFile.exists(); //check if temp file exits
					//if file exists, assign default variables to user input
					if (exists) {
						System.out.println("File updated.");
						userDay = userDayInput;
						userMonth = userMonthInput;
						userYear = userYearInput;
						break;
					}
					// if the file does not exist, let user know and allow user to try again or use default
					else {
						System.out.println("File does not exist, try again by pressing enter or if you want to use default file select 'd'");
						String i = input.nextLine().toLowerCase(Locale.ROOT);
						if (i.trim().equals("d")) {
							break;
						} //use default and break from loop
						else {
							continue; //continue to loop
						}
					}

				}
				break;
			} else {
				System.out.println("Incorrect selection, try again.");
			}
		}


			// researchers table
			// read from excel sheet
			try {
				// File parentDir =
				// currentDir.getParentFile().getParentFile().getParentFile().getParentFile();
				// File newFile = new File(parentDir,"Example.txt");;
				// File newFile = new File(".");
				// File n = new File("../../" + newFile);
				// read from the excel file
				FileInputStream file = new FileInputStream(
						new File(userDirectory
								+ "\\src\\main\\resources\\Current-Rated-Researchers-" + userDay + "-" + userMonth + "-" + userYear + ".xlsx"));
				System.out.println("Reading from file Current-Rated-Researchers-" + userDay + "-" + userMonth + "-" + userYear + ".xlsx file.");
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();


				//int count = 0;
				// iterate through rows
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() == 0 || row.getRowNum() == 1) {
						continue;
					} else {

						Iterator<Cell> cellIterator = row.cellIterator();
						String surname = "";
						String initials = "";
						String title = "";
						String institution = "";
						String rating = "";
						Date start = null;
						Date end = null;
						String primary = "";
						String secondary = "";
						String specialisations = "";
						boolean checkPrimary = false;
						boolean checkSecondary = false;
						boolean checkSpecial = false;

						// iterate through cell
						int index = 0;
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							index++;
							if (index == 1) {
								surname = cell.getStringCellValue();
							} else if (index == 2) {
								initials = cell.getStringCellValue();
							} else if (index == 3) {
								title = cell.getStringCellValue();
							} else if (index == 4) {
								institution = cell.getStringCellValue();
							} else if (index == 5) {
								rating = cell.getStringCellValue();
							} else if (index == 6) {
								start = cell.getDateCellValue();
							} else if (index == 7) {
								end = cell.getDateCellValue();
							} else if (index == 8) {
								primary = cell.getStringCellValue();
								// sort by AI researchers
								if (primary.contains("Artificial intelligence")
										|| primary.contains("Artificial Intelligence")) {
									checkPrimary = true;
									// indexPrimary++;
								}
							} else if (index == 9) {
								secondary = cell.getStringCellValue();
								// sort by AI researchers

								String seclow = secondary.toLowerCase();

								if (seclow.contains("artificial intelligence")) {
									checkSecondary = true;
								}


							} else if (index == 10) {
								specialisations = cell.getStringCellValue();
								// sort by AI researchers

								String speclow = specialisations.toLowerCase();
								if (speclow.contains("artificial intelligence") || speclow.contains("machine learning") || speclow.contains("computer vision") || speclow.contains("deep learning")
										|| speclow.contains("natural language processing") || speclow.contains("neural networks") || speclow.contains("bayesian learning") || speclow.contains("monte carlo search tree") ||
										speclow.contains("reinforcement learning") || speclow.contains("speech recognition") || speclow.contains("speech processing") ||
										speclow.contains("pattern recognition")) {

									checkSpecial = true;
									// indexSecondary++;
								}
							}

							// System.out.println(cell);
						}

						// create researcher object after reading cell by cell
						// beforeFilter++;
						// only add researcher to the database if their research field/specialisation
						// contains AI
						if (checkPrimary == true || checkSecondary == true || checkSpecial == true) {
							Researcher r = new Researcher(surname, initials, title, institution, rating, start, end,
									primary, secondary, specialisations);
							researcherService.createResearcher(r); // add researcher to the SQL database
							//count++;

							// System.out.println(r);
							// System.out.println("");
							// afterFilter++;
						}
					}
					file.close();
					workbook.close();
				}
				//System.out.println("NUM RESEARCHERS " + count);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println();
			}

			// ResearcherArticles table
			// read from excel sheet
			try {

				FileInputStream file = new FileInputStream(
						new File(userDirectory
								+ "\\src\\main\\resources\\ResearchPapers.xlsx"));
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();

				// iterate through rows
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() == 0 || row.getRowNum() == 1) {
						continue;
					} else {

						Iterator<Cell> cellIterator = row.cellIterator();
						String pub = "";
						String auth = "";
						String names = "";
						String title = "";
						String sourceTitle = "";

						String language = "";
						String type = "";
						String conTitle = "";
						String conDate = "";
						String conLocation = "";

						String keyword = "";
						String keyPlus = "";
						String abs = "";
						int citeCount = 0;
						int dayCount = 0;
						int sinceCount = 0;

						String issn = "";
						String abb = "";
						String year = "";
						String doi = "";
						String doiLink = "";

						// iterate through cell
						int index = 0;
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							index++;
							if (index == 1) {
								pub = cell.getStringCellValue();
							} else if (index == 2) {
								auth = cell.getStringCellValue();
							} else if (index == 3) {
								names = cell.getStringCellValue();
							} else if (index == 4) {
								title = cell.getStringCellValue();
							} else if (index == 5) {
								sourceTitle = cell.getStringCellValue();
							} else if (index == 6) {
								language = cell.getStringCellValue();
							} else if (index == 7) {
								type = cell.getStringCellValue();
							} else if (index == 8) {
								conTitle = cell.getStringCellValue();
							} else if (index == 9) {
								conDate = cell.getStringCellValue();
							} else if (index == 10) {
								conLocation = cell.getStringCellValue();
							} else if (index == 11) {
								keyword = cell.getStringCellValue();
							} else if (index == 12) {
								keyPlus = cell.getStringCellValue();
							} else if (index == 13) {
								abs = cell.getStringCellValue();
							} else if (index == 14) {
								citeCount = (int) cell.getNumericCellValue();
							} else if (index == 15) {
								dayCount = (int) cell.getNumericCellValue();
							} else if (index == 16) {
								sinceCount = (int) cell.getNumericCellValue();
							} else if (index == 17) {
								issn = cell.getStringCellValue();
							} else if (index == 18) {
								abb = cell.getStringCellValue();
							} else if (index == 19) {
								if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
									year = String.valueOf(cell.getNumericCellValue());
								} else {
									year = cell.getStringCellValue();
								}
							} else if (index == 20) {


								//NEED TO POTENTIALLY ADD CODE HERE TO REPLACE / VALUE
								doi = cell.getStringCellValue();
							} else if (index == 21) {
								doiLink = cell.getStringCellValue();
							}

						}
						ResearchArticles r = new ResearchArticles(pub, auth, names, title,
								sourceTitle, language, type, conTitle,
								conDate, conLocation, keyword, keyPlus,
								abs, citeCount, dayCount, sinceCount, issn,
								abb, year, doi, doiLink);
						researchArticlesService.createResearcher(r);
						file.close();
						workbook.close();

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println();
			}
		}

	}
