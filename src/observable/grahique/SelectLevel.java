package observable.grahique;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class SelectLevel extends JDialog implements ActionListener{

	private final JPanel contentPanel;
	private final JPanel panCenter;
	private final JLabel labLevel;
	private final JButton [] tabJbutton = new JButton [3];
//	private final JButton btnRetour;
	private final Menu2 frame;


	/**
	 * Create the dialog.
	 */
	public SelectLevel(Menu2 fenetre) {
		this.frame=fenetre;
		this.contentPanel = new JPanel();
		this.setBounds(100, 100, 1200, 700);
		this.getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setLayout(new FlowLayout());
		this.contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setResizable(false);
		this.setModal(true);

//		this.panNorth = new JPanel();
//		this.panNorth.setPreferredSize(new Dimension(1200, 75));
//		this.panNorth.setBackground(new Color(255, 255, 255));
//		this.panNorth.setLayout(null);
//
//		this.panSouth = new JPanel();
//		this.panSouth.setPreferredSize(new Dimension(1200, 75));
//		this.panSouth.setBackground(new Color(255, 255, 255));
//		this.panSouth.setLayout(null);

		this.panCenter = new JPanel();
		this.panCenter.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		this.panCenter.setBackground(new Color(255, 255, 255));
		this.panCenter.setLayout(null);

		this.labLevel = new JLabel("Basics");
		labLevel.setFont(new Font("Serif", Font.PLAIN, 25));
		this.labLevel.setBounds(this.getSize().width/2,0,182,34);

//		this.panNorth.add(this.labLevel);

		int x = 30;
		int y = 30;
		for(int i=0;i<3;i++)
		{

			//Modifier la taille
			this.tabJbutton[i] = new JButton(""+(i+1));
			this.tabJbutton[i].setBounds(x, y, 100, 100);
			this.tabJbutton[i].setBackground(new Color(255, 255, 255));
			this.tabJbutton[i].addActionListener(this);
			this.panCenter.add(this.tabJbutton[i]);

			//placement des images
			x+=140;
			if ((i+1)%6==0)
			{
				y+=140;
				x=30;
			}
		}
		
		setBackgr();

//		this.btnRetour = new JButton("Menu");
//		this.btnRetour.setBackground(new Color(255, 255, 255));
//		this.btnRetour.setBounds(15, 16, 109, 29);
//		this.btnRetour.addActionListener(this);
//		this.panSouth.add(this.btnRetour);

//		this.contentPanel.add(this.panNorth,BorderLayout.NORTH);
		this.contentPanel.add(this.panCenter, BorderLayout.CENTER);
//		this.contentPanel.add(this.panSouth,BorderLayout.SOUTH);


		this.setContentPane(this.contentPanel);

	}


	public void setBackgr() {
		ImageIcon icon = new ImageIcon("selectLvl/back.jpg");
		Image img = icon.getImage() ;
		Image newimg = img.getScaledInstance( this.getWidth(), this.getHeight()+100,  java.awt.Image.SCALE_SMOOTH ) ;
		icon = new ImageIcon( newimg );
		JLabel backMenu1 = new JLabel(icon);
		backMenu1.setBounds(0, -10, this.getWidth(), this.getHeight());
		
		panCenter.add(backMenu1);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		if(e.getSource()==this.btnRetour){
//			this.dispose();
//		}

		if(e.getSource()==tabJbutton[0]){
			this.dispose();
			Jeu game = new Jeu(0,this.frame);
		}

	}

}
