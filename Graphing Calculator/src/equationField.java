import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class equationField extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel label = new JLabel("f(x) =");
	public JTextField equation = new JTextField(50);
	private JCheckBox cbox = new JCheckBox();
	public ArrayList<graphPoint> points;
	public boolean is_set = false;
	public calcWindow calc;

	public boolean hasEquation() {
		return this.cbox.isSelected() && this.is_set ;
	}

	public String getEquation() {
		return this.equation.getText();
	}

	public ArrayList<graphPoint> results;

	public equationField() {
		calc = new calcWindow(this);
		label.setAlignmentX(0);
		// Box hbox = Box.createHorizontalBox();
		this.add(cbox);
		this.add(label);
		this.add(equation);

		equation.setSize(50, 0);

		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				AbstractButton abstractButton = (AbstractButton) itemEvent
						.getSource();

				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					calc.setVisible(true);
				}
				else if (state == ItemEvent.DESELECTED) {
					calc.setVisible(false);
				}
			}
		};

		cbox.addItemListener(itemListener);
		

		this.setLayout(new FlowLayout());
		this.setSize(60, 10);

	}

}