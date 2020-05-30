package hust.soict.hedspi.visualization.action;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

public class ArrayVisualizer {
	// Do khoi tao doi tuong moi trong moi lan goi constructor
	// ==> Attribute la static -> bao toan gia tri cua lan truoc
	public int num;
	private JPanel pnImitiate;
	private ElementBox[] elementBoxs;
	
	public void setPnImitiate(JPanel pnImitiate) {
		this.pnImitiate = pnImitiate;
	}
	
	public void setElementBoxs(ElementBox[] elementBoxs) {
		this.elementBoxs = elementBoxs;
	}
	
	public ElementBox[] getElementBoxs() {
		return elementBoxs;
	}

	// Connstructor --> create array visualization
	public ArrayVisualizer(JPanel pnImitiate, JSpinner spNum, ElementBox[] elementBoxs) {
		// Set pnImitiate of ArrayVisualizer
		setPnImitiate(pnImitiate);
		// Delete older array
		deleteArray();
		// Get number
		num = (Integer)spNum.getValue();
		elementBoxs = new ElementBox[num];
		
		for (int i = 0; i < num; i++) {
			// Create element box
			elementBoxs[i] = new ElementBox();
			pnImitiate.add(elementBoxs[i].getLabel());
			//set location label
			if (i == 0)
				elementBoxs[i].getLabel().setLocation(((int) ((18 - num) * 0.5) * 70) + 100, 150);
			else
				elementBoxs[i].getLabel().setLocation(elementBoxs[i-1].getLabel().getX() + 70, 150);
		}
		// Set elementBoxs --> use delete, setZero, createRandom...
		setElementBoxs(elementBoxs);
		pnImitiate.setVisible(true);
		pnImitiate.validate();
		pnImitiate.repaint();
//		setState(1);
	}
	
	public void deleteArray() {
		for (int i = 0; i < num; i++) {
			elementBoxs[i].setText("0");
			elementBoxs[i].setValue(0);
			elementBoxs[i].getLabel().setVisible(false);
			pnImitiate.remove(elementBoxs[i].getLabel());
		}
		
		for (int i = 0; i < SortVisualizer.curT; i++) {
			try {
					SortVisualizer.threads[i].interrupt();
			} 
			catch (Exception e) {
				
			}
		}
		SortVisualizer.curT = -1;
		
		pnImitiate.revalidate();
		pnImitiate.repaint();
//		setState(0);
	}
	
	public void createRandom() {
		Random rand = new Random();
		for (int i = 0; i < num; i++) {
			int ranNum = rand.nextInt(101) + 0;
			elementBoxs[i].getLabel().setText(String.valueOf(ranNum));
//			lbArrays[i].setForeground(Color.BLUE);
			elementBoxs[i].setValue(ranNum);
		}
		pnImitiate.setVisible(true);
		pnImitiate.validate();
		pnImitiate.repaint();
//		setState(2);
	}

	public void setZero() {
		for (int i = 0; i < num; i++) {
			elementBoxs[i].setText("0");
			elementBoxs[i].setValue(0);
		}
		
		this.pnImitiate.revalidate();
		this.pnImitiate.repaint();
//		setState(1);
	}
	
	public void createHand() {
		// GUI enter elements of array
		JButton btnOK;
		JSpinner[] txtArrays = new JSpinner[num];
		JLabel[] lbArrays = new JLabel[num];
		
		JFrame arrayInput = new JFrame();
		arrayInput.setTitle("Nh\u1EADp d\u1EEF li\u1EC7u m\u1EA3ng");
		arrayInput.setBounds(100, 100, 504, 312);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		arrayInput.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		arrayInput.setLocation(dim.width/2-arrayInput.getSize().width/2, dim.height/2-arrayInput.getSize().height/2);
		for (int i = 0; i < num; i++) {
			lbArrays[i] = new JLabel("A[" + i + "]:");
			SpinnerModel smValue = new SpinnerNumberModel(0, 0, 100, 1);
			txtArrays[i] = new JSpinner(smValue);
			JFormattedTextField txt = ((JSpinner.NumberEditor) txtArrays[i].getEditor()).getTextField();
			((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
			contentPane.add(lbArrays[i]);
			contentPane.add(txtArrays[i]);
			lbArrays[i].setSize(40,30);
			if (i == 0 || i == 5 || i == 10) 
				lbArrays[i].setLocation(150 * (i + 1)/5  , 30);
			else
				lbArrays[i].setLocation(lbArrays[i-1].getX(), lbArrays[i-1].getY() + 40);
			txtArrays[i].setSize(50,30);
			txtArrays[i].setLocation(lbArrays[i].getX() + 40, lbArrays[i].getY());
		}
		contentPane.setVisible(true);
		contentPane.validate();
		contentPane.repaint();
		
		btnOK = new JButton("Xác nhận");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < num; i++) {
					elementBoxs[i].setValue((int) txtArrays[i].getValue());
					elementBoxs[i].setText(String.valueOf(elementBoxs[i].getValue()));
				}
				pnImitiate.setVisible(true);
				pnImitiate.validate();
				pnImitiate.repaint();
				JOptionPane.showMessageDialog(arrayInput, "Đã tạo dữ liệu mảng thành công!\nChuẩn bị thoát!");
				arrayInput.dispatchEvent(new WindowEvent(arrayInput, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnOK.setBackground(SystemColor.activeCaption);
		btnOK.setBounds(185, 237, 120, 25);
		contentPane.add(btnOK);
		arrayInput.setVisible(true);
	}
	
	
}
