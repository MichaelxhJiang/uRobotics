/*===========================================================================================================================================================================================================================
 * Space Alien Game
 * Jerry Qu
 * 6/4/2017
 * Java: Eclipse Neon.2 Release (4.6.2)
 * ===========================================================================================================================================================================================================================
 * Problem Definition - Required to write a mathematical game to help young math students learn about the Cartesian plane 
 * 						and locate items on a grid. In this game you are a Space Alien Hunter.  The goal of this game is to 
 * 						capture as many space aliens by entering their (x,y) coordinates or clicking to find the randomly appearing 
 * 						aliens (appearing ONE AT A TIME) as quickly and accurately as possible within a time limit or after 
 * 						so many lives. Each incorrect input takes away one of your game lives. Alien locations will be
 * 						randomly generated and stored in a 2D array. Aliens can not appear on the same coordinate twice in
 * 						a round. Bonus points are awarded for clicking the alien as fast as possible. The game must keep
 * 						track of the score, timer, and lives during the game as well as the current highscore in the session,
 * 						highscore, accuracy, and average hitspeed, after the game. The game must also have an option to
 * 						view the placements of all aliens after the game. The game should also have a visual interface
 * 						, be error proof, and store the highest scores in an input/output file. Finally, the program should
 * 						 include proper programming style and conventions including displaying the title of the program 
 * 						at the very beginning.  Commenting is to be included for all lines of code including all closing brackets.
 * Input - Specified in seperate classes
 * 		   MainMenu:
 * 			User clicks either the highscore, game, chooseFile, hotToPlay, or soundEffects JButton as input.
 * 			Each will lead to a seperate result
 * Output - Specified in seperate classes
 * 		   MainMenu:
 * 			soundEffects JButton's text is changed to either ON or OFF
 * 			highscoreFileLabel is configured to display the path of the highscoreFile
 * 			highscoreFileLabel is configured to display if the highscoreFile is un-configured to store highscores
 * Process - Setup & Coordinate all JPanels in a JFrame
 * 		   MainMenu:
 * 			check if the file selected by the user has been configured to store highscores
 * 			create new file if the highscoreFile does not exist or if there was an error creating a new file 
 * 			 
 * ===========================================================================================================================================================================================================================
 *List of global Identifiers
 * SIZE - variable used to control the length and width of the grid for the game <type final static int>
 * highscoreFile - variable used to store the name of the highscore file <type static String>
 * clip - variable used to store the background music clip <type static Clip>
 * soundEffects - variable used to store whether or not to play sound effects <type boolean>
 * gameFrame - object used to hold all of the JPanels in one window <type JFrame>
 * mainMenuPanel - object used to hold and organize all elements in the main menu of the program <type JPanel>
 * howToPlayPanel - object used to hold and organize all elements in the how to play menu of the program <type JPanel>
 * gamePanel - object used to hold and organize the grid in the game part of the program <type JPanel>
 * highScorePanel - object used to hold and organize all elements in the highscore menu of the program <type JPanel>
 * gameOverMenuPanel - object used to hold and organize all elements in the game over menu of the game <type JPanel>
 * gameLabels - object used to hold and organize the labels in the game part of the program <type JPanel>
 * gameHold - object used to hold and organize all elements in the game part of the program <type JPanel>
 * mainMenu - object used to initiate and configure all elements in the mainMenuPanel <type mainMenu>
 * howToPlay - object used to initiate and configure all elements in the howToPlayPanel <type HowToPlay>
 * gameOverMenu - object used to initiate and configure all elements in the gameOverMenuPanel <type GameOverMenu>
 * game - object used to initiate and configure all elements in the gamePanel <type Game>
 * fc - object used to access an audio file for playing by the music method <type final JFileChooser>
 * 
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;
import javax.swing.*;
public class SpaceAlien extends JFrame implements ActionListener { // working
	final static int SIZE = 7;
	static String highscoreFile = "HighScore";
	static Clip clip;
	static boolean soundEffects = true;
	static JFrame gameFrame = new JFrame( "Grid Clicker" );
	JPanel mainMenuPanel = new JPanel();
	JPanel howToPlayPanel = new JPanel();
	JPanel highScorePanel = new JPanel();
	JPanel gamePanel = new JPanel();
	JPanel gameOverMenuPanel = new JPanel();
	JPanel gameLabels = new JPanel();
	JPanel gameHold = new JPanel();
	MainMenu mainMenu = new MainMenu(this, highscoreFile);
	HowToPlay howToPlay = new HowToPlay(this, howToPlayPanel, mainMenuPanel);
	HighScore highScore = new HighScore(this, highScorePanel);
	GameOverMenu gameOverMenu = new GameOverMenu(gameOverMenuPanel, mainMenuPanel, gameFrame, gameHold);
	Game game = new Game(SIZE, gameOverMenu, gameOverMenuPanel, gameLabels, gameHold, mainMenuPanel);
	final JFileChooser fc = new JFileChooser();
	/**main method
	 * This procedural method is called automatically and is used to create a new SpaceAlein object to play the game
	 * 
	 * List of Local Variables
	 * play - an object used to initiate the game <type SpaceAlien>
	 * 
	 * @param args <type String>
	 * 
	 * @return void
	 */
	public static void main( String args[] )  {
		SpaceAlien test = new SpaceAlien();
		test.make();
	}
	/**actionPerformed method
	 * This procedural method is called automatically when a JButton is clicked. If the JButton highScore is clicked,
	 * the visibility of the mainMenuPanel will be set to false, and the visibility of the highScorePanel will be
	 * set to true. If the game JButton is clicked, the play method in the game object is run and the JPanels' visibilities
	 * are toggled to display the gamePanel. This will run the game. If the mainMenu button is clicked from the highScorePanel,
	 * the JPanels' visibilities are toggled to display the mainMenuPanel. If the chooseFile button is clicked,
	 * the program will make a file selector pop-up which will record the path of the file selected in the variable highscoreFile.
	 * Every '\' that is in the String will be duplicated to become '\\' as java will not read a single '\'. The highscoreFile
	 * will than be read and checked to make sure it has been configured to store highscores. If the file does not exist, the
	 * program will create a new file with the name entered. If the file exists but has data within it that has not been setup
	 * to store highscores, the program will prompt the user to select a different file. If the JButton howToPlay is
	 * clicked, the JPanels' visibilities are toggled to display the howToPlayPanel. Finally, if the JButton soundEffects is
	 * clicked, the soundEffects boolean will be toggled to store whether or not to play soundEffects
	 * 
	 * List of Local Variables
	 * searchScore - object used to read data from the highscoreFile <type BufferedReader>
	 * fileReject - variable used to check for errors in reading or creating a new file <type boolean>
	 * highscoreFileError - object used to record error messages in reading the highscoreFile <type Exception>
	 * fileNotFound - object used to catch errors where highscoreFile can not be found <type FileNotFoundException>
	 * fileCreation - object used to catch errors in creating a new text file <type Exception>
	 * unconfigured - object used to catch errors in reading the text file <type NumberFormatException | IOException>
	 * 
	 * 
	 * @param e - object used to get the source of the JButton that was clicked <type ActionEvent>
	 * 
	 * @return void
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mainMenu.getHighScore()) {
			mainMenuPanel.setVisible(false);
			try {
				highScore.drawHighscore(highscoreFile, highScorePanel, mainMenuPanel, mainMenu);
			} catch(Exception f) {
				
			}
			highScorePanel.setVisible(true);
		}
		else if(e.getSource() == mainMenu.getGame()) {
			System.out.println("Start");
			mainMenuPanel.setVisible(false);
			gameLabels.setVisible(true);
			gamePanel.setVisible(true);
			gameHold.setVisible(true);
			game.play(SIZE, game, 0, gameOverMenu, gameOverMenuPanel, true, gameHold, highscoreFile, soundEffects);
		}
		else if(e.getSource() == highScore.getButton()) {
			highScorePanel.setVisible(false);
			mainMenuPanel.setVisible(true);
		}
		else if(e.getSource() == mainMenu.chooseFile) {
			boolean fileReject = true;
			while(fileReject) {
				fileReject = false;
				int returnVal = fc.showOpenDialog(this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					highscoreFile = file.getPath();
					for(int i=0;i<highscoreFile.length();i++) {
						if(highscoreFile.substring(i, i+1).equals("\\")) {
							highscoreFile = highscoreFile.substring(0, i+1) + "\\" + highscoreFile.substring(i+1, highscoreFile.length());
							i++;
						}
					}
					mainMenu.draw(highscoreFile);
				}
				BufferedReader searchScore;
				try {
					searchScore = new BufferedReader(new FileReader(highscoreFile));
					long[] highscores = new long[10];
					for(int i=0;i<10;i++) {
						try {
							highscores[i] = Long.parseLong(searchScore.readLine());
						} catch(NumberFormatException | IOException e2) {
							mainMenu.highscoreFileLabel.setText("**WARNING** FILE HAS UNCONFIGURED DATA WITHIN IT, PLEASE SELECT ANOTHER FILE");
							System.out.println("**WARNING** FILE HAS DATA WITHIN IT AND HAS NOT BEEN SET UP TO STORE HIGHSCORES, PLEASE SELECT ANOTHER FILE");
							fileReject = true;
						}
					}
				} catch(FileNotFoundException e1) {
					try {
						BufferedWriter writeScore = new BufferedWriter(new FileWriter(highscoreFile));
						searchScore = new BufferedReader(new FileReader(highscoreFile));
						for(int i=0;i<10;i++) {
							writeScore.write("0");
							writeScore.newLine();
						}
						writeScore.close();
					} catch(Exception e2) {
						mainMenu.highscoreFileLabel.setText("ERROR, creating new file automatically");
						highscoreFile = "AutoHighscore";
					}
					fileReject = true;
				}
			}
		}
		else if(e.getSource() == mainMenu.howToPlay) {
			mainMenuPanel.setVisible(false);
			howToPlayPanel.setVisible(true);
		}
		else if(e.getSource() == mainMenu.soundEffects) {
			clip.stop();
			soundEffects = !soundEffects;
			if(soundEffects)
				mainMenu.soundEffects.setText("Sound Effects: ON");
			else
				mainMenu.soundEffects.setText("Sound Effects: OFF");
			music("MainMenuMusic.wav");
		}
	}
	/**music method
	 * This procedural method will play a .wav audio file, specified by the parameter file. If soundEffects is true
	 * the audioFile object is created, passing the argument file. The program will try to call
	 * .open(AudioSystem.getAudioInputSteam(audioFile) on the clip object and catch any error which will set error
	 * to true. The clip will start 10 frames ahead as changed by the method setLoopPoints. The method 
	 * loop(Clip.LOOP_CONTINUOUSLY) will put the clip on loop and finally the clip will start with the start
	 * method if there are no errors, which is checked through the error boolean
	 * 
	 * List of Local Variables
	 * error - variable used to check if an error has occurred which will terminate the rest of the code <type boolean>
	 * 
	 * @param file - variable used to receive the name of the audio file to be played <type String>
	 * 
	 * @return void
	 */
	public static void music(String file) {
		if(soundEffects) {
			boolean error = false;
			File audioFile = new File(file);
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(audioFile));
			} catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
				error = true;
			}
			if(!error) {
				clip.setLoopPoints(10, clip.getFrameLength()-1);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				clip.start();
			}
		}
	}
	/**setUpJPanel method
	 * This procedural method configures the JPanels and adds them to the JFrame frame
	 * 
	 * List of Local Variables
	 * screenSize - object used to get the screen width and height of the computer the program is being run on <type Dimension>
	 * 
	 * @param frame - object to put JPanels on <type JFrame>
	 * @param panel - JPanel to configure (setSize & setVisible) <type JPanel>
	 * @param panel - variable that specifies whether to set the visibility of the JPanel to true or false <type boolean>
	 * 
	 * @return void
	 */
	public void setUpJPanel(JFrame frame, JPanel panel, boolean visible) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		panel.setSize(screenSize.width, screenSize.height);
		panel.setVisible(visible);
		frame.add(panel);
	}
	/**make method
	 * This procedural method initializes the game. It creates the JFrame, JPanels to put on the JFrame, and
	 * the objects to put on the JPanels. It also calls the music method to play background music.
	 * 
	 * List of Local Variables
	 * screenSize - variable used to get the screen size of the local computer <type Dimension>
	 * 
	 * @return void
	 */
	public void make() {
		music("MainMenuMusic.wav");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainMenuPanel.add(mainMenu);
		setUpJPanel(gameFrame, mainMenuPanel, true);
		highScorePanel.add(highScore);
		setUpJPanel(gameFrame, highScorePanel, false);
		gamePanel.add(game);
		setUpJPanel(gameFrame, gamePanel, false);
		gameHold.setLayout(new BoxLayout(gameHold, BoxLayout.PAGE_AXIS));
		gameHold.add(gameLabels);
		gameHold.add(gamePanel);
		setUpJPanel(gameFrame, gameHold, false);
		gameOverMenuPanel.add(gameOverMenu);
		setUpJPanel(gameFrame, gameOverMenuPanel, false);
		howToPlayPanel.add(howToPlay);
		setUpJPanel(gameFrame, howToPlayPanel, false);
		gameFrame.add(mainMenuPanel);
		gameFrame.add(highScorePanel);
		gameFrame.add(gameHold);
		gameFrame.add(gameOverMenuPanel);
		gameFrame.add(howToPlayPanel);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		gameFrame.setSize(screenSize.width, screenSize.height);
	}
}