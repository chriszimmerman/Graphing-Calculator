

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class calcWindow extends JFrame {

	private JPanel buttonpanel = new JPanel();

	private JPanel calcpanel = new JPanel();
	private JButton[] numbuttons;
	private JButton[] opbuttons;
	private JButton[] funcbuttons;
	private String[] primops = { "(", ")","*", "/", "+", "-", "~" };
	private String[] funcops = { "sin", "cos", "tan", "log", "sqrt" };
	final calcWindow win = this;
	private JTextField calcfield = new JTextField(30);
	private JMenu file = new JMenu();
	public equationField out;
	// private void init_opButtons() {

	private class opPanel extends JPanel {
		public opPanel() {
			GridLayout experimentLayout = new GridLayout(6, 0, 5, 5);
			this.setLayout(experimentLayout);

			opbuttons = new JButton[5];

			for (int i = 0; i < opbuttons.length; i++) {

				opbuttons[i] = new JButton();
				opbuttons[i].setText(primops[i]);

				final String currentop = primops[i];
				opbuttons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						calcfield.setText(calcfield.getText()+" " + currentop + " ");

					}
				});
				this.add(opbuttons[i]);

			}
		}

	}

	private class funcopPanel extends JPanel {
		public funcopPanel() {
			GridLayout experimentLayout = new GridLayout(6, 0, 5, 5);
			this.setLayout(experimentLayout);

			funcbuttons = new JButton[5];

			for (int i = 0; i < funcbuttons.length - 1 ; i++) {

				funcbuttons[i] = new JButton();
				funcbuttons[i].setText(funcops[i]);

				final String currentop = funcops[i] + "( ";
				funcbuttons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						calcfield.setText(calcfield.getText()+ " " +currentop );

					}
				});
				
				
				
				this.add(funcbuttons[i]);

			}
			
			
			JButton x = new JButton();
			 x.setText("x");			
			 x.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					calcfield.setText(calcfield.getText()+ " x ");

				}
			});
			
			this.add(x);
			
		}

	}

	
	
	private class numPanel extends JPanel {

		public numPanel() {
			numbuttons = new JButton[13];

			GridLayout experimentLayout = new GridLayout(4, 3, 5, 5);
			this.setLayout(experimentLayout);
			this.setSize(100, 100);

			for (int i = 1; i < numbuttons.length - 3; i++) {
				numbuttons[i] = new JButton();
				numbuttons[i].setText(String.valueOf(i));
				final int current = i;
				numbuttons[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						calcfield.setText(calcfield.getText()+ current);

					}

				});
				this.add(numbuttons[i]);
			}

			// zero button
			numbuttons[0] = new JButton();
			numbuttons[0].setText(String.valueOf(0));
			final int current1 = 0;

			numbuttons[0].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					calcfield.setText(calcfield.getText()+ current1);
				}
			});

			this.add(numbuttons[0]);

			numbuttons[11] = new JButton();
			numbuttons[11].setText(".");
			numbuttons[11].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					calcfield.setText(calcfield.getText()+ ".");
				}
			});

			this.add(numbuttons[11]);

			// equals sign place button
			numbuttons[12] = new JButton();
			numbuttons[12].setText("=");
			
			
			numbuttons[12].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					out.equation.setText(calcfield.getText());
					out.is_set = true;
					
					win.setVisible(false);
				}
			});

			this.add(numbuttons[12]);

		}
	}

	private void initpanel() {
		calcpanel.setLayout(new BoxLayout(calcpanel, BoxLayout.Y_AXIS));

		opPanel operatorbuttons = new opPanel();
		funcopPanel funcbuttons = new funcopPanel();
		numPanel numbuttons = new numPanel();
		// the text box for simple calculations and the number grid
		Box calcpanel = Box.createVerticalBox();

		calcpanel.add(calcfield);
		calcpanel.add(numbuttons);

		// Box mainPanel = Box.createHorizontalBox();

		JPanel mainPanel = new JPanel();

		mainPanel.add(calcpanel);
		mainPanel.add(operatorbuttons);
		mainPanel.add(funcbuttons);
		this.add(mainPanel);

		// menu stuff
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu viewMenu = new JMenu("View");
		JMenuItem grapwindowAction = new JMenuItem("Graph");
		viewMenu.add(grapwindowAction);

		menuBar.add(fileMenu);
		menuBar.add(viewMenu);

		this.setTitle("Enter An Equation");
		pack();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public calcWindow(equationField equation) {

		
		out = equation;
		this.initpanel();

	}



}
