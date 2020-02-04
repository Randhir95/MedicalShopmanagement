package komalhealthcare;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class SetInputBoxType implements KeyListener
{
    int type;
    int maxsize;
    int cases;
    public SetInputBoxType(int s,int t,int c)
    {
        maxsize=s;
        type=t;
        cases=c;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()=='\n'||e.getKeyChar()=='\b'||e.getKeyChar()=='\t')return;
        JTextComponent input=(JTextComponent)e.getSource();
        if(maxsize!=0&&input.getText().length()==maxsize)
        {
            e.setKeyChar('\0');
            return;
        }
        if(type!=0)
        {
            if(type==StaticMember.INTEGER_TYPE)
            {
                if(!(e.getKeyChar()>='0'&&e.getKeyChar()<='9'))
                {
                    e.setKeyChar('\0');
                    return;
                }
            }
            else if(type==StaticMember.FLOAT_TYPE)
            {
                int dot=input.getText().indexOf(".");
                if(dot!=-1&&input.getText().length()==dot+3)
                {
                    e.setKeyChar('\0');
                    return;
                }
                else if(!((e.getKeyChar()>='0'&&e.getKeyChar()<='9')||(dot==-1&&e.getKeyChar()=='.')))
                {
                    e.setKeyChar('\0');
                    return;
                }
            }
            else if(type==StaticMember.ALFABET_TYPE)
            {
                if(!((e.getKeyChar()>='A'&&e.getKeyChar()<='Z')||(e.getKeyChar()>='a'&&e.getKeyChar()<='z')))
                {
                    e.setKeyChar('\0');
                    return;
                }
            }
        }
        if(cases!=0)
        {
            if(cases==StaticMember.UPPER_CASE)
            {
                String ch=(e.getKeyChar()+"").toUpperCase();
                e.setKeyChar(ch.charAt(0));
            }
            else if(cases==StaticMember.LOWER_CASE)
            {
                String ch=(e.getKeyChar()+"").toLowerCase();
                e.setKeyChar(ch.charAt(0));
            }
            else if(cases==StaticMember.TITTAL_CASE)
            {
                String ch=e.getKeyChar()+"";
                if((input.getText().equals(""))||input.getText().charAt(input.getText().length()-1)==' '||input.getText().charAt(input.getText().length()-1)=='\n')
                    ch=ch.toUpperCase();
                else
                    ch=ch.toLowerCase();
                e.setKeyChar(ch.charAt(0));
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
