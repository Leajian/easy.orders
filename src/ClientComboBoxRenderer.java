import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ClientComboBoxRenderer extends JLabel implements ListCellRenderer {

	public ClientComboBoxRenderer()
	{
		setOpaque(true);
	}
	
	  /*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
    public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus)
    {
        //Get the selected index. (The index param isn't
        //always valid, so just use the value.)
        //int selectedIndex = ((Integer)value).intValue();

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        if (value != null)
        {
        	String clientLabel = ((Client)value).getName();
        	setText(clientLabel);
        }
        else
        	setText("");

        return this;
    }
}
