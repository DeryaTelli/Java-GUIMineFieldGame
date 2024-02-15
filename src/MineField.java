import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class MineField implements MouseListener {
    JFrame frame;
    Button [][] board=new Button[10][10];
    int openButton;

    public MineField(){
        openButton=0;
        frame = new JFrame("Mine Field");
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(10,10));

        for(int row=0; row<board.length;row++){
            for(int col=0; col<board[0].length;col++){
                Button button =new Button(row,col);
                frame.add(button);
                button.addMouseListener(this);
                board[row][col]=button;
            }
        }

        generateMine();
        updateCount();
        //print();
        showMine();



        frame.setVisible(true);

    }


    public void generateMine(){
       int i=0;
       while(i<10){
           int randomRow=(int) (Math.random()*board.length);
           int randomCol=(int) (Math.random()*board[0].length);
           while(board[randomRow][randomCol].isMine()){
               randomRow=(int) (Math.random()*board.length);
               randomCol=(int) (Math.random()*board[0].length);
           }
           board[randomRow][randomCol].setMine(true);
           i++;
       }
    }
    public void print(){
        for(int row=0; row<board.length;row++){
            for(int col=0; col<board[0].length;col++){
               if(board[row][col].isMine()){
                   board[row][col].setIcon( new ImageIcon("src/mine.png"));
               }else{
                   board[row][col].setText( board[row][col].getCount()+"");//string cevirdik
                   board[row][col].setEnabled(false);

               }
            }
        }

    }

    public void showMine(){
        for(int row=0; row<board.length;row++){
            for(int col=0; col<board[0].length;col++){
                if(board[row][col].isMine()){
                    board[row][col].setIcon( new ImageIcon("src/mine1.png"));
                }
            }
        }
    }


    public void updateCount(){
        for(int row=0; row<board.length;row++){
            for(int col=0; col<board[0].length;col++){
                if(board[row][col].isMine()){
                    counting(row,col);
                }

            }
        }

    }
    public void counting(int row ,int col){
        for(int i=row-1;i<=row+1;i++){
            for(int k=col-1; k<col+1;k++){
               try{
                   int value = board[i][k].getCount();
                   board[i][k].setCount(++value);
               }catch (Exception e) {

               }



            }

        }
    }

    public void open(int r , int c){
        if(r<0 || r>= board.length || c<0 || c>=board[0].length || board[r][c].getText().length()>0 || board[r][c].isEnabled()==false){
            return;
        }else if(board[r][c].getCount()!=0){
            board[r][c].setText(board[r][c].getCount()+"");
            board[r][c].setEnabled(false);
            openButton++;
        }else{
            openButton++;
            board[r][c].setEnabled(false);
            open(r-1,c);
            open(r+1,c);
            open(r,c-1);
            open(r,c+1);

        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
       Button button= (Button) e.getComponent(); // componentin tipini belirtmek zorundasin
       if(e.getButton()==1){
           System.out.println("left click");
           if(button.isMine()){
               JOptionPane.showMessageDialog(frame,"You step on mine. Game over !!!");
               print();
           }else{
               open(button.getRow(), button.getCol());
               if(openButton==(board.length*board[0].length)-10){
                   JOptionPane.showMessageDialog(frame," You won !!");

               }



           }

       }else if(e.getButton()==3){
           System.out.println("right click");
           if(!button.isFlag() ){
               button.setIcon(new ImageIcon("src/flag1.png"));
               button.setFlag(true);
           }else{
               button.setIcon(null);
               button.setFlag(false);
           }
       }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
