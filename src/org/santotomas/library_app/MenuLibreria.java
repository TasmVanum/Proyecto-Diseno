package org.santotomas.library_app;

import org.santotomas.library_app.dao.BookDAO;
import org.santotomas.library_app.dao.Database;
import org.santotomas.library_app.models.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MenuLibreria extends JFrame implements ActionListener {

    // region Components
    private JPanel pnlPanel;
    private JTabbedPane tbdHome;
    private JPanel pnlBuscar;
    private JTextField txtBuscar;
    private JCheckBox chkCategoriaTerror;
    private JCheckBox chkCategoriaFantasia;
    private JCheckBox chkCategoriaMagia;
    private JCheckBox chkCategoriaSuspenso;
    private JCheckBox chkCategoriaRomance;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JLabel lblCategorias;
    private JPanel pnlAgregar;
    private JTextField txtNombreLibro;
    private JTextField txtAutor;
    private JTextField txtDescripcion;
    private JTextField txtStock;
    private JPanel pnlCerrarSesion;
    private JButton btnCloseSession;
    private JScrollPane jspLibros;
    private JTable table1;
    private JLabel lblNombreLibro;
    private JLabel lblDescripcion;
    private JLabel lblCategoria;
    private JLabel lblAutor;
    private JLabel lblStock;
    private JButton btnAgregar;
    private JPanel pCategorias;
    private JLabel lblPrecio;
    private JSpinner spnPrecio;
    private JSpinner spnStock;
    private JRadioButton rMagia;
    private JRadioButton rSuspenso;
    private JRadioButton rTerror;
    private JRadioButton rFantasia;
    private JRadioButton rRomance;
    // endregion

    private Database myDatabase;
    private DefaultTableModel dtmLibros;
    private ButtonGroup bgCategorias;

    /**
     * Constructor en el cual definimos lo primero que haga nuestra ventana de menu al crearse (instanciarse)
     * @param titulo titulo de la ventana
     * @param nombre nombre del usuario
     */
    public MenuLibreria(String titulo, String nombre) throws SQLException, ClassNotFoundException {
        /* Configuramos nuestra ventana */
        super(titulo + nombre);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1280,640));
        setPreferredSize(new Dimension(1280,640));
        setVisible(true);

        String contraSantiago = "1324";
        String contraGaston = "";
        myDatabase = new Database("localhost", "library", "root", contraSantiago);

        // region Buttons & Mnemonics
        tbdHome.setMnemonicAt(0, KeyEvent.VK_1);
        tbdHome.setMnemonicAt(1, KeyEvent.VK_2);
        tbdHome.setMnemonicAt(2, KeyEvent.VK_3);

        btnAgregar.setMnemonic(KeyEvent.VK_A);
        btnActualizar.setMnemonic(KeyEvent.VK_A);
        btnEliminar.setMnemonic(KeyEvent.VK_E);

        btnAgregar.addActionListener(this);
        btnActualizar.addActionListener(this);
        btnEliminar.addActionListener(this);

        // region Radio Buttons Group
        bgCategorias = new ButtonGroup();
        bgCategorias.add(rMagia);
        bgCategorias.add(rSuspenso);
        bgCategorias.add(rTerror);
        bgCategorias.add(rRomance);
        bgCategorias.add(rFantasia);

        rMagia.setActionCommand("Magia");
        rSuspenso.setActionCommand("Suspenso");
        rTerror.setActionCommand("Terror");
        rFantasia.setActionCommand("Fantasia");
        rRomance.setActionCommand("Romance");
        // endregion

        btnCloseSession.setIcon(new ImageIcon("src/org/santotomas/library_app/img/close_icon.png"));
        btnCloseSession.addActionListener(this);
        // endregion

        dtmLibros = new DefaultTableModel();
        dtmLibros.addColumn("ISBN");
        dtmLibros.addColumn("Titulo");
        dtmLibros.addColumn("Descripcion");
        dtmLibros.addColumn("Precio");
        dtmLibros.addColumn("Categoria");
        dtmLibros.addColumn("Autor(a)");
        dtmLibros.addColumn("Estado");
        dtmLibros.addColumn("Stock");
        dtmLibros.addColumn("Fecha Salida");
        table1.setModel(dtmLibros);

        updateTable();

        /** add panel */

        btnCloseSession.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                btnCloseSession.setIcon(new ImageIcon("src/org/santotomas/library_app/img/close_icon_pressed.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                btnCloseSession.setIcon(new ImageIcon("src/org/santotomas/library_app/img/close_icon.png"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnCloseSession.setIcon(new ImageIcon("src/org/santotomas/library_app/img/close_icon_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnCloseSession.setIcon(new ImageIcon("src/org/santotomas/library_app/img/close_icon.png"));
            }
        });

        add(pnlPanel);
        pack();
    }

    public void updateTable() throws SQLException {
        BookDAO bookDAO = new BookDAO(myDatabase);
        List<Book> books = bookDAO.getAll();

        for (int i = dtmLibros.getRowCount(); i > 0; i--) {
            dtmLibros.removeRow(i - 1);
        }

        for (Book book : books) {
            dtmLibros.addRow(new Object[] {
                    book.getIsbn(),
                    book.getTitle(),
                    book.getDescription(),
                    book.getPrice(),
                    book.getCategoryId(),
                    book.getAuthor(),
                    book.getEstate(),
                    book.getStock(),
                    book.getRelease_date()
            });
        }
    }

    /**
     * Funcion que nos obliga a configurar el comportamiento de nuestros botones
     * @param e parámetro que captura el evento
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == btnAgregar) {
            try {
                String titulo = txtNombreLibro.getText();
                String autor = txtAutor.getText();
                String descripcion = txtDescripcion.getText();
                int precio = Integer.parseInt(spnPrecio.getValue().toString());
                int stock = Integer.parseInt(spnStock.getValue().toString());
                int categoria = 0;

                switch (bgCategorias.getSelection().getActionCommand()) {
                    case "Magia":
                        categoria = 1;
                        break;
                    case "Suspenso":
                        categoria = 2;
                        break;
                    case "Terror":
                        categoria = 3;
                        break;
                    case "Fantasia":
                        categoria = 4;
                        break;
                    case "Romance":
                        categoria = 5;
                        break;

                    default:
                        categoria = 0;
                }

                if (titulo.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Libro sin titulo", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Book book = new Book();
                    book.setTitle(titulo);
                    book.setAuthor(autor);
                    book.setDescription(descripcion);
                    book.setPrice(precio);
                    book.setStock(stock);
                    book.setCategoryId(categoria);

                    BookDAO bookDAO = new BookDAO(myDatabase);

                    bookDAO.add(book);

                    // print result in table
                    updateTable();

                    // reset form
                    txtNombreLibro.setText("");
                    txtAutor.setText("");
                    txtDescripcion.setText("");
                    spnPrecio.setValue(0);
                    spnStock.setValue(0);

                    rFantasia.setSelected(false);
                    rRomance.setSelected(false);
                    rTerror.setSelected(false);
                    rSuspenso.setSelected(false);
                    rMagia.setSelected(false);

                    txtNombreLibro.requestFocus();

                    JOptionPane.showMessageDialog(this, "Libro registrado exitosamente", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Palabras en Stock o en precio", "Error", JOptionPane.ERROR_MESSAGE);
                ex.getStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        if ( e.getSource() == btnActualizar) {
            Book book = new Book(
                    String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 0)),
                    String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 1)),
                    String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 2)),
                    Integer.parseInt(String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 3))),
                    Integer.parseInt(String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 4))),
                    String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 5)),
                    String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 6)),
                    Integer.parseInt(String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 7))),
                    Date.valueOf(String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 8)))
                    );

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UpdateBook updateBook = new UpdateBook(book);
                        updateTable();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }


            });
        }

        if (e.getSource() == btnEliminar) {

            String uuid = String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 0));
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Está seguro", "Eliminar " + dtmLibros.getValueAt(table1.getSelectedRow(), 1),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            switch (option) {
                case JOptionPane.YES_OPTION:
                    BookDAO bookDAO = new BookDAO(myDatabase);
                    try {
                        bookDAO.delete(uuid);
                        updateTable();
                        JOptionPane.showMessageDialog(this, "Libro eliminado exitosamente", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;

                case JOptionPane.NO_OPTION:

                    break;
            }
        }

        if ( e.getSource() == btnCloseSession ) {
            dispose();
            try {
                new Login("Bienvenid@");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }
}
