package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.time.Year.isLeap;

public class Patient extends JPanel {
    protected int y, m, d;
    protected JButton labs[][];
    protected int leadGap = 0;

    Calendar calendar = new GregorianCalendar();
    protected final int thisYear = calendar.get(Calendar.YEAR);
    protected final int thisMonth = calendar.get(Calendar.MONTH);
    private JButton b0;
    private JComboBox monthChoice;
    private JComboBox yearChoice;

    Patient(){
        super();
        setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        buildGUI();
        recompute();
    }

    Patient(int year, int month, int today) {
        super();
        setDate(year, month, today);
        buildGUI();
        recompute();
    }

    private void setDate(int year, int month, int today) {
        y = year;
        m = month;
        d = today;
    }

    String[] months = {"Janvier", "Février", "Mars", "Avril","Mai",
    "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

    private void buildGUI(){
        getAccessibleContext().setAccessibleDescription("Not accessible");
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.add(monthChoice = new JComboBox());
        for (int i=0; i<months.length; i++)
            monthChoice.addItem(months[m]);
        monthChoice.setSelectedItem(months[m]);
        monthChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = monthChoice.getSelectedIndex();
                if (i>=0){
                    m = i;
                    recompute();
                }
            }
        });
        monthChoice.getAccessibleContext().setAccessibleName("Months");
        monthChoice.getAccessibleContext().setAccessibleDescription("Choose a month.");
        panel.add(yearChoice = new JComboBox());
        yearChoice.setEditable(true);
        for (int i=y - 5; i<y; i++)
            yearChoice.addItem(Integer.toString(i));
        yearChoice.setSelectedItem(Integer.toString(y));
        yearChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = yearChoice.getSelectedIndex();
                if (i>=0){
                    y = Integer.parseInt(yearChoice.getSelectedItem().toString());
                    recompute();
                }
            }
        });
        add(BorderLayout.CENTER, panel);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(7, 7));
        labs = new JButton[6][7];

        panel2.add(b0 = new JButton("Lundi"));
        panel2.add(new JButton("Mardi"));
        panel2.add(new JButton("Mercredi"));
        panel2.add(new JButton("Jeudi"));
        panel2.add(new JButton("Vendredi"));
        panel2.add(new JButton("Samedi"));
        panel2.add(new JButton("Dimanche"));

        ActionListener dateSetter = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String num = e.getActionCommand();
                if (!num.equals("")) {
                    setDayActive(Integer.parseInt(num));
                }
            }
        };

        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++) {
                panel2.add(labs[i][j] = new JButton(""));
                labs[i][j].addActionListener(dateSetter);
            }
        add(BorderLayout.SOUTH, panel2);
    }

    public final static int dom[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    protected void recompute(){
        if (m < 0 || m > 11)
            throw new IllegalArgumentException("Month " + m + " bad, must be 0-11");
        clearDayActive();
        calendar = new GregorianCalendar(y, m, d);
        leadGap = new GregorianCalendar(y, m, 1).get(Calendar.DAY_OF_WEEK) - 1;

        int daysInMonth = dom[m];
        if (isLeap(calendar.get(Calendar.YEAR)) && m > 1)
        ++daysInMonth;

        for (int i = 0; i < leadGap; i++) {
            labs[0][i].setText("");
        }

        for (int i = 1; i <= daysInMonth; i++) {
            JButton b = labs[(leadGap + i - 1) / 7][(leadGap + i - 1) % 7];
            b.setText(Integer.toString(i));
        }

        for (int i = leadGap + 1 + daysInMonth; i < 6 * 7; i++) {
            labs[(i) / 7][(i) % 7].setText("");
        }

        if (thisYear == y && m == thisMonth)
        setDayActive(d);

        repaint();
    }

    public boolean isLeap(int year) {
        if ( year % 100 != 0 || year % 400 == 0)
        return true;
        return false;
    }

    public void setTheDate(int year, int month, int day) {
        // System.out.println("Cal::setDate");
        this.y = year;
        this.m = month;
        this.d = day;
        recompute();
    }

    private void clearDayActive() {
        JButton b;

        // First un-shade the previously-selected square, if any
        if (activeDay > 0) {
            b = labs[(leadGap + activeDay - 1) / 7][(leadGap + activeDay - 1) % 7];
            b.setBackground(b0.getBackground());
            b.repaint();
            activeDay = -1;
        }
    }

    private int activeDay = -1;

    /** Set just the day, on the current month */
    public void setDayActive(int newDay) {
        clearDayActive();
        if (newDay <= 0)
            d = new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
        else
            d = newDay;
        Component square = labs[(leadGap + newDay - 1) / 7][(leadGap + newDay - 1) % 7];
        square.setBackground(Color.red);
        square.repaint();
        activeDay = newDay;
    }

    public static void main(String[] argv)
    {
        JFrame f = new JFrame("Mon Calendrier");
        Container c = f.getContentPane();
        c.setLayout(new FlowLayout());

        // Initialiser un calendrier avec la date suivant
        c.add(new Patient(1990, 2, 15));

        // and beside it, the current month.
        c.add(new Patient());

        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

