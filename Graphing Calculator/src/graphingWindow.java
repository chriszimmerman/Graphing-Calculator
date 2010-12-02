import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.imageio.*;

import java.awt.image.*;
import java.io.*;

public class graphingWindow extends JFrame {

	calcController control = new calcController();

	private static final long serialVersionUID = 1L;
	// grid dimensions, be careful about how close this gets to the window sizwe
	int gridwidth = 100;
	int gridheight = 100;
	JButton graphit = new JButton("Plot Graphs");
	// variables for the window dimensions
	int windowheight = 800;
	int windowwidth = 800;

	// a local refrence to the canvas graphics
	Graphics2D localgraphics;

	// a button which prints the map state

	// different states for grid squares

	// spacing for grid lines in pixels, calculated at run time
	int vertspacing;
	int horzspacing;

	// a data model for the grid, uses the gridsquare class for storage

	// a class local refrence to a canvas
	private JPanel cnvs;
	private JPanel cnvsPanel = new JPanel();

	// private JPanel equations = new JPanel();

	// private JPanel panel = new JPanel();

	equationField eq1 = new equationField();
	equationField eq2 = new equationField();
	equationField eq3 = new equationField();
	equationField eq4 = new equationField();

	/*
	 * Function: drawgrid desc: redraws the map grid according to the specified
	 * grid dimension and window size
	 */

	private void draw_line_offset(Graphics2D g2, int x1, int y1, int x2, int y2) {

		g2.drawLine(28 + x1, cnvs.getHeight() - y1 - 28, x2 + 28,
				cnvs.getHeight() - y2 - 28);

	}

	private void mouseclick(MouseEvent e) {
		int x = (int) ((e.getX() - 1) / (this.cnvs.getWidth() / this.gridwidth));
		int y = (int) ((e.getY() - 1) / (this.cnvs.getHeight() / this.gridheight));

		this.drawplane();
	}

	public void drawplane() {

		// set up the colors and get access to the graphics for the canvas
		Graphics2D g2 = (Graphics2D) this.cnvs.getGraphics();

		if (g2 == null)
			return;
		g2.setColor(java.awt.Color.white);

		// y axis
		g2.fillRect(0, 0, this.cnvs.getWidth(), this.cnvs.getHeight());
		g2.setColor(java.awt.Color.black);

		// x axis
		g2.drawLine(28, 0, 28, this.cnvs.getHeight());
		g2.drawLine(0, this.cnvs.getHeight() - 28, this.cnvs.getWidth(),
				this.cnvs.getHeight() - 28);

		if (eq1.hasEquation()) {
			this.plotResult(g2, eq1.results);
		}
		if (eq2.hasEquation()) {
			this.plotResult(g2, eq2.results);
		}
		if (eq3.hasEquation()) {
			this.plotResult(g2, eq3.results);
		}
		if (eq4.hasEquation()) {
			this.plotResult(g2, eq4.results);
		}

	}

	private void plotResult(Graphics2D g2, ArrayList<graphPoint> points) {

		if (points == null || points.size() == 0)
			return;
		for (int i = 0; i <= points.size() - 1; i++) {
			int x1 = (int) points.get(i).x;
			int y1 = (int) points.get(i).y;
			int x2;
			int y2;
			if (i + 1 <= points.size() - 1) {

				x2 = (int) points.get(i + 1).x;
				y2 = (int) points.get(i + 1).y;
			} else {

				x2 = (int) points.get(i).x;
				y2 = (int) points.get(i).y;
			}
			draw_line_offset(g2, x1, y1, x2, y2);
		}

	}

	public graphingWindow() {
		this.setMinimumSize(new Dimension(500, 500));

		this.cnvs = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void update(Graphics g) {
				
				paint(g);
			}

			protected void paintComponent(Graphics g) {
				drawplane();
			}

			public void paint(Graphics g) {				
				drawplane();

			}
		};

		cnvs.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				drawplane();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				drawplane();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});

		cnvs.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// due to scopeing rules, the mouse event has to be pass
				// to the outer class to access the canvas.
				mouseclick(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		final JFrame shit = (JFrame) this;
		graphit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (eq1.hasEquation()) {
					try {
						eq1.results = control.runEq(eq1.getEquation());

					} catch (validationException e) {

						JOptionPane.showMessageDialog(shit, e.getMessage(),
								"Error in Equation 1",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						System.out.print("\n");

					} catch (Exception e) {
						JOptionPane.showMessageDialog(shit, e.getMessage(),
								"Error in Equation 1",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						System.out.print("\n");
						e.printStackTrace();
					}

				}
				if (eq2.hasEquation()) {
					try {
						eq2.results = control.runEq(eq2.getEquation());
					} catch (validationException e) {
						JOptionPane.showMessageDialog(shit, e.getMessage(),
								"Error in Equation 1",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						System.out.print("\n");

					} catch (Exception e) {
						JOptionPane.showMessageDialog(shit, e.getMessage(),
								"Error in Equation 1",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						System.out.print("\n");
						e.printStackTrace();
					}

				}
				if (eq3.hasEquation()) {
					try {
						eq3.results = control.runEq(eq3.getEquation());
					} catch (validationException e) {
						JOptionPane.showMessageDialog(shit, e.getMessage(),
								"Error in Equation 1",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						System.out.print("\n");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(shit, e.getMessage(),
								"Error in Equation 1",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						System.out.print("\n");
						e.printStackTrace();
					}

				}
				if (eq4.hasEquation()) {
					try {
						eq4.results = control.runEq(eq4.getEquation());
					} catch (validationException e) {
						JOptionPane.showMessageDialog(shit, e.getMessage(),
								"Error in Equation 1",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						System.out.print("\n");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				drawplane();

			}

		});

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		JPanel equations = new JPanel();
		equations.setBorder(BorderFactory.createTitledBorder("Equations:"));
		equations.setLayout(new BoxLayout(equations, BoxLayout.PAGE_AXIS));
		equations.add(eq1);
		equations.add(eq2);
		equations.add(eq3);
		equations.add(eq4);

		this.cnvsPanel.setBorder(BorderFactory.createTitledBorder("Graphs:"));
		
		panel.add(new JScrollPane(equations));
		panel.add(graphit);

		JSplitPane mainsplit = new JSplitPane(0, true, new JScrollPane(cnvs),
				panel);

		// mainsplit.setDoubleBuffered(true);
		mainsplit.setOneTouchExpandable(true);
		this.add(mainsplit);

		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		// menu stuff
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu viewMenu = new JMenu("View");
		JMenuItem grapwindowAction = new JMenuItem("Graph");
		viewMenu.add(grapwindowAction);

		grapwindowAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// drawplane();
			}
		});

		grapwindowAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * 
				 * 
				 * 
				 * 
				 * final JFileChooser fc = new JFileChooser();
				 * fc.setFileFilter(new ImageFilter()); int returnVal =
				 * fc.showSaveDialog(graphingWindow.this);
				 * 
				 * if (returnVal == JFileChooser.APPROVE_OPTION) { File file =
				 * fc.getSelectedFile(); // This is where a real application
				 * would open the file.
				 * 
				 * 
				 * 
				 * BufferedImage image = ScreenImage.createImage(cnvs);
				 * 
				 * try { ImageIO.write(image, "jpg", file); } catch (IOException
				 * e) { e.printStackTrace(); }
				 * 
				 * }
				 */

			}

		});

		grapwindowAction.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				// due to scopeing rules, the mouse event has to be pass
				// to the outer class to access the canvas.
				drawplane();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.addComponentListener(new ComponentListener(){

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				drawplane();
				
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				drawplane();
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
			
		});
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		setJMenuBar(menuBar);
		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setTitle("Graphing Calculator");
		//pack();
		// this.setResizable(false);
		// this.pack();
		drawplane();
		mainsplit.setDividerLocation(0.75);
	}
}
