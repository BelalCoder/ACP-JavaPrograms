package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class MainFrame extends JFrame {

    JTextField txtFName, txtLName, txtAge, txtEmail, txtSearch;
    JTable table;
    JTextArea status;
    StudentDAO dao = new StudentDAO();

    public MainFrame() {
        setTitle("StudentDB Manager");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 2));
        txtFName = new JTextField();
        txtLName = new JTextField();
        txtAge = new JTextField();
        txtEmail = new JTextField();
        txtSearch = new JTextField();

        form.add(new JLabel("First Name")); form.add(txtFName);
        form.add(new JLabel("Last Name")); form.add(txtLName);
        form.add(new JLabel("Age")); form.add(txtAge);
        form.add(new JLabel("Email")); form.add(txtEmail);
        form.add(new JLabel("Search ID")); form.add(txtSearch);

        add(form, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(
                new String[]{"ID", "First Name", "Last Name", "Age", "Email"}, 0
        ));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Add Student");
        JButton btnView = new JButton("View Students");
        JButton btnSearch = new JButton("Search Student");

        JPanel buttons = new JPanel();
        buttons.add(btnAdd); buttons.add(btnView); buttons.add(btnSearch);
        add(buttons, BorderLayout.SOUTH);

        status = new JTextArea(3, 20);
        status.setEditable(false);
        add(new JScrollPane(status), BorderLayout.EAST);

        btnAdd.addActionListener(e -> addStudent());
        btnView.addActionListener(e -> loadStudents());
        btnSearch.addActionListener(e -> searchStudent());
    }

    private void addStudent() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() {
                Student s = new Student(
                        txtFName.getText(),
                        txtLName.getText(),
                        Integer.parseInt(txtAge.getText()),
                        txtEmail.getText()
                );
                dao.addStudent(s);
                return null;
            }
            protected void done() { status.setText("Student added successfully."); }
        };
        worker.execute();
    }

    private void loadStudents() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() {
                try {
                    ResultSet rs = dao.viewStudents();
                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getInt("age"),
                                rs.getString("email")
                        });
                    }
                } catch (Exception e) { e.printStackTrace(); }
                return null;
            }
            protected void done() { status.setText("Students loaded."); }
        };
        worker.execute();
    }

    private void searchStudent() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() {
                try {
                    ResultSet rs = dao.searchStudent(Integer.parseInt(txtSearch.getText()));
                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getInt("age"),
                                rs.getString("email")
                        });
                    }
                } catch (Exception e) { e.printStackTrace(); }
                return null;
            }
            protected void done() { status.setText("Search completed."); }
        };
        worker.execute();
    }
}
