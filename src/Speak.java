import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class Speak {
	Synthesizer synthesizer;
	JLabel text,check,numResult,time,lastLabel;
	JButton start, reset,pause, checkout;
	JLabel labels[]=new JLabel[90];
	JPanel panel;
	JTextField input,last;
	JComboBox cb;
	JFrame frame=new JFrame();
	float flo=0;
	boolean isStart=true;
	SwingWorker<Void, Void> worker2;
	ArrayList<Integer> list=null;
	ArrayList<Integer> old=null;
	String placeholder="Example: 18,22,9,12,21";
	public Speak() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		frame.setVisible(true);
		frame.setTitle("Play Buddy");
		frame.setSize((int)(frame.getWidth()), (int)(0.999*frame.getHeight()));
		frame.getContentPane().setLayout(null);
		text=new JLabel("Go!",JLabel.CENTER);
		panel=new JPanel();
		panel.setBackground(new Color(240,230,140));
		panel.setLayout(null);
		text.setFont(new Font("Calibri", Font.BOLD, 200));
		text.setBackground(new Color(0,191,255));
		text.setOpaque(true);
		
		time=new JLabel("Time(sec) gap between numbers");
		time.setFont(new Font("Calibri", Font.BOLD, 12));
		String[] options={"1","2","3","4","5","6","7"};
		cb=new JComboBox(options);
		cb.setSelectedItem("3");
		cb.setFocusable(false);
		start=new JButton("Start");
		start.setFocusable(false);
		reset=new JButton("Reset");
		reset.setEnabled(false);
		reset.setFocusable(false);
		pause=new JButton("Pause");
		pause.setFocusable(false);
		pause.setEnabled(false);
		checkout=new JButton("Check");
		checkout.setFocusable(false);
		
		lastLabel=new JLabel("Last 5 numbers ");
		lastLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		last=new JTextField(" ");
		last.setEditable(false);
		last.setFont(new Font("Calibri", Font.BOLD, 18));
		
		check=new JLabel("Numbers Check:");
		check.setFont(new Font("Calibri", Font.BOLD, 18));
		numResult=new JLabel(" ");
		numResult.setFont(new Font("Calibri", Font.BOLD, 13));
		input=new JTextField(placeholder);
		input.setForeground(Color.GRAY);
		input.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				 if (input.getText().equals(placeholder)) {
			            input.setText("");
			            input.setForeground(Color.BLACK);
			        }
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				 if (input.getText().isEmpty()) {
			            input.setForeground(Color.GRAY);
			            input.setText(placeholder);
			        }
			}
			
		});
		
		for(int i=0;i<90;i++) {
			labels[i]=new JLabel(String.valueOf(i+1),JLabel.CENTER);
			labels[i].setEnabled(false);
			labels[i].setBackground(Color.LIGHT_GRAY);
			labels[i].setOpaque(true);
			labels[i].setFont(new Font("Calibri", Font.BOLD, 18));
		}
		
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				worker2 = new SwingWorker<Void, Void>() {
					   @Override
					   protected Void doInBackground() throws Exception {
						  old=new ArrayList<Integer>();
						   try {
							   int struck=0;
							list=anyNumber();
							reset.setEnabled(true);
							pause.setEnabled(true);
							start.setEnabled(false);
							last.setText(" ");
							for(int i=0;i<list.size();i++) {
								if(pause.getText().equalsIgnoreCase("Pause")) {
									struck=i;
								text.setText(list.get(i).toString());
								labels[list.get(i)-1].setEnabled(true);
								labels[list.get(i)-1].setBackground(new Color(57,255,20));
								speech(list.get(i).toString());
								old.add(list.get(i));
								String num5=" ";
								for(int j=old.size()-5;j<old.size();j++) {
									if(j>=0) {
									num5=num5+old.get(j)+", ";
									}
								}
								last.setText(num5.substring(0, num5.length()-2));
								try {
							Thread.sleep(Integer.parseInt(cb.getSelectedItem().toString())*1000);
								} catch (InterruptedException e) {
							System.out.println("It is reseted");
								}
								if(i==list.size()-1) {
							
							text.setText("Done!");
							speech("Done!");
								}

							}
								else {
									i=struck;
								}
							}
							
						} catch (Exception e) {
							System.out.println("Error is occured because of Reset");
						}
					   
				 return null;
					   }
					  };
				worker2.execute();
				
			}
			
		});
		reset.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            worker2.cancel(true);
	            isStart=true;
	            list=null;
	            old=null;
	            text.setText("Go!");
	            speech(text.getText());
	            pause.setEnabled(false);
	            start.setEnabled(true);
	            reset.setEnabled(false);
	            last.setText(" ");
	            for(int i=0;i<90;i++) {
					labels[i].setEnabled(false);
					labels[i].setBackground(Color.LIGHT_GRAY);
				}
			
	        }

	    });
		
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(pause.getText().equalsIgnoreCase("Pause")) {
				pause.setText("Resume");
				reset.setEnabled(false);
				}
				else {
					pause.setText("Pause");
					reset.setEnabled(true);
				}
			}
			
		});
		
		checkout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String result=input.getText();
				if(!result.equals(placeholder)&&!result.trim().equals("")) {
				try {
					String[] spilt=result.split(",");
					boolean isExist=false;
					ArrayList<Integer> output=new ArrayList<Integer>();
					for(String list:spilt) {
						int num=Integer.parseInt(list.trim());
						try {
							for(Integer res:old) {
								if(res==num) {
									isExist=true;
									break;
								}
								else {
									isExist=false;
								}
							}
						} catch (Exception e) {
							numResult.setForeground(Color.RED);
							numResult.setText("Huh! Brainless idiot.. How can I check numbers without start the game?");
							break;
						}
						if(!isExist) {
							output.add(num);
						}
					}
					String wrongnum="";
					if(output.size()==0) {
						if(!numResult.getText().equals("Huh! Brainless idiot.. How can I check numbers without start the game?")) {
						numResult.setForeground(Color.BLUE);
						numResult.setText("Stupid why don't you concentrate. Those numbers are already come");
						}
					}else {
						boolean more=false;
						for(Integer wrong:output) {
							wrongnum=wrongnum+wrong+", ";
							if(wrong>90) {
								more=true;
								break;
							}
						}
						if(more) {
							numResult.setForeground(Color.RED);
							numResult.setText("Shh Doggy! You entered more than 90 number that means you don't know about game. First learn and then play");
						}else {
						numResult.setForeground(Color.RED);
						numResult.setText("Waste fellow "+wrongnum.substring(0,wrongnum.length()-2)+" didn't come yet. Don't hurry");
						}
					}
				} catch (NumberFormatException e) {
					numResult.setForeground(Color.RED);
					numResult.setText("Bloody uneducated fellow.. Why are you entering alphabets? Try again");
				}
			}
				else {
					numResult.setForeground(Color.RED);
					numResult.setText("Hey African animal! First enter numbers yaar or else how can I know your numbers");
				}
			}
			
		});
		
		input.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				numResult.setText(" ");
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				numResult.setText(" ");
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				numResult.setText(" ");
			}
			
		});
		
		input.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent key) {
				if(key.getKeyChar()==KeyEvent.VK_ENTER) {
					checkout.doClick();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		
		for(int i=0;i<90;i++) {
			if((i+1)%10==1) {
			labels[i].setBounds((int)(0.5*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==81) {
				flo=0;
			}
			}
		}
		
		for(int i=0;i<90;i++) {
			if((i+1)%10==2) {
			labels[i].setBounds((int)(0.55*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==82) {
				flo=0;
			}
			}
		}
		
		for(int i=0;i<90;i++) {
			if((i+1)%10==3) {
			labels[i].setBounds((int)(0.6*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==83) {
				flo=0;
			}
			}
		}
		
		for(int i=0;i<90;i++) {
			if((i+1)%10==4) {
			labels[i].setBounds((int)(0.65*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==84) {
				flo=0;
			}
			}
		}
		
		for(int i=0;i<90;i++) {
			if((i+1)%10==5) {
			labels[i].setBounds((int)(0.7*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==85) {
				flo=0;
			}
			}
		}
		
		for(int i=0;i<90;i++) {
			if((i+1)%10==6) {
			labels[i].setBounds((int)(0.75*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==86) {
				flo=0;
			}
			}
		}
		
		for(int i=0;i<90;i++) {
			if((i+1)%10==7) {
			labels[i].setBounds((int)(0.8*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==87) {
				flo=0;
			}
			}
		}
		
		for(int i=0;i<90;i++) {
			if((i+1)%10==8) {
			labels[i].setBounds((int)(0.85*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==88) {
				flo=0;
			}
			}
		}
		
		for(int i=0;i<90;i++) {
			if((i+1)%10==9) {
			labels[i].setBounds((int)(0.9*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==89) {
				flo=0;
			}
			}
		}
		for(int i=0;i<90;i++) {
			if((i+1)%10==0) {
			labels[i].setBounds((int)(0.95*frame.getWidth()), (int)(0.05*frame.getHeight()+flo), (int)(0.025*frame.getWidth()), (int)(0.03*frame.getHeight()));
			flo=(float) (flo+0.075*frame.getHeight());
			if((i+1)==90) {
				flo=0;
			}
			}
		}
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		text.setBounds((int)(0*frame.getWidth()), (int)(0*frame.getHeight()), (int)(0.49*frame.getWidth()), (int)(0.8*frame.getHeight()));
		time.setBounds((int)(0.5*frame.getWidth()), (int)(0.75*frame.getHeight()), (int)(0.2*frame.getWidth()), (int)(0.03*frame.getHeight()));
		lastLabel.setBounds((int)(0.81*frame.getWidth()), (int)(0.75*frame.getHeight()), (int)(0.15*frame.getWidth()), (int)(0.03*frame.getHeight()));
		last.setBounds((int)(0.875*frame.getWidth()), (int)(0.75*frame.getHeight()), (int)(0.1*frame.getWidth()), (int)(0.03*frame.getHeight()));
		cb.setBounds((int)(0.625*frame.getWidth()), (int)(0.75*frame.getHeight()), (int)(0.05*frame.getWidth()), (int)(0.03*frame.getHeight()));
		panel.setBounds((int)(0*frame.getWidth()), (int)(0.8*frame.getHeight()), (int)(frame.getWidth()), (int)(0.2*frame.getHeight()));
		start.setBounds((int)(0.05*panel.getWidth()), (int)(0.3*panel.getHeight()), (int)(0.075*panel.getWidth()), (int)(0.2*panel.getHeight()));
		reset.setBounds((int)(0.212*panel.getWidth()), (int)(0.3*panel.getHeight()), (int)(0.075*panel.getWidth()), (int)(0.2*panel.getHeight()));
		pause.setBounds((int)(0.375*panel.getWidth()), (int)(0.3*panel.getHeight()), (int)(0.075*panel.getWidth()), (int)(0.2*panel.getHeight()));
		check.setBounds((int)(0.5*panel.getWidth()), (int)(0.1*panel.getHeight()), (int)(0.2*panel.getWidth()), (int)(0.2*panel.getHeight()));
		input.setBounds((int)(0.5*panel.getWidth()), (int)(0.3*panel.getHeight()), (int)(0.25*panel.getWidth()), (int)(0.2*panel.getHeight()));
		checkout.setBounds((int)(0.76*panel.getWidth()), (int)(0.3*panel.getHeight()), (int)(0.075*panel.getWidth()), (int)(0.2*panel.getHeight()));
		numResult.setBounds((int)(0.5*panel.getWidth()), (int)(0.55*panel.getHeight()), (int)(0.5*panel.getWidth()), (int)(0.2*panel.getHeight()));
		
		frame.getContentPane().add(text);
		frame.getContentPane().add(cb);
		frame.getContentPane().add(time);
		frame.getContentPane().add(lastLabel);
		frame.getContentPane().add(last);
		frame.getContentPane().add(panel);
		panel.add(start);
		panel.add(reset);
		panel.add(pause);
		panel.add(check);
		panel.add(input);
		panel.add(checkout);
		panel.add(numResult);
		for(int i=0;i<90;i++) {
			frame.getContentPane().add(labels[i]);
		}
		frame.setResizable(false);
	}
	
	public void speech(String text) {
		 try
	     {
	         // set property as Kevin Dictionary
	         System.setProperty("freetts.voices",
	             "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory"); 
	              
	         // Register Engine
	         Central.registerEngineCentral
	             ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
	         
	         // Create a Synthesizer
	         synthesizer =                                         
	             Central.createSynthesizer(new SynthesizerModeDesc(Locale.ENGLISH));    
	  
	         // Allocate synthesizer
	         synthesizer.allocate();        
	          
	         // Resume Synthesizer
	         synthesizer.resume();    
	          
	         // speaks the given text until queue is empty.
	         synthesizer.speakPlainText(text, null);         
	         synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
	          
	         // Deallocate the Synthesizer.
//	         synthesizer.deallocate();                                 
	     } 

	     catch (Exception e) 
	     {
	        System.out.println("Going to start new game");
	     }
	}
	
	public ArrayList<Integer> anyNumber() {
		ArrayList<Integer> list=new ArrayList<Integer>();
		Random rand=new Random();
		boolean is=true;
		while(true) {
		int number=rand.nextInt(90)+1;
		for(int i=0;i<list.size();i++) {
			if(number==list.get(i)) {
				is=false;
				break;
			}
			else {
				is=true;
			}
		}
		if(is) {
			list.add(number);
				}
		
		if(list.size()==90) {
			System.out.println(list.size());
			break;
		}
		}
		return list;
	}
	
	
public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				Speak sp = new Speak();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	
	ArrayList<Integer> last=new ArrayList<Integer>();
	last.add(47);
	last.add(58);
	last.add(15);
//	last.add(30);
//	last.add(16);
//	last.add(9);
//	last.add(67);
//	last.add(40);
	
	for(int i=last.size()-5;i<last.size();i++) {
		if(i>=0)
		System.out.println(last.get(i).toString());
	}
	
}
}
