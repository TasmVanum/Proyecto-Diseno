package org.santotomas.library_app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.santotomas.library_app.dao.BookDAO;
import org.santotomas.library_app.dao.Database;
import org.santotomas.library_app.models.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private JLabel lblPrecio;
    private JSpinner spnPrecio;
    private JSpinner spnStock;
    private JPanel pnlGrafico;
    private JPanel pnlContenidoGrafico;
    private JPanel pnlGraficador;
    private JTextField txtSemana1;
    private JTextField txtSemana2;
    private JTextField txtSemana3;
    private JTextField txtSemana4;
    private JButton btnGraficar;
    private JButton btnActualizarTabla;
    private JList listCategory;
    private DefaultListModel listCategoryModel;
    // endregion

    private Database myDatabase;
    private DefaultTableModel dtmLibros;



    /**
     * Constructor en el cual definimos lo primero que haga nuestra ventana de menu al crearse (instanciarse)
     * @param titulo titulo de la ventana
     * @param nombre nombre del usuario
     */
    public MenuLibreria(String titulo, String nombre) throws SQLException, ClassNotFoundException {
        /* Configuramos nuestra ventana */
        super(titulo + nombre);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 640));
        setPreferredSize(new Dimension(1280, 640));
        setVisible(true);

        //Colores tbdHome
        pnlPanel.setBackground(Color.decode("#212121"));
        tbdHome.setBackgroundAt(0, Color.decode("#212121"));
        tbdHome.setBackgroundAt(1, Color.decode("#212121"));
        tbdHome.setBackgroundAt(2, Color.decode("#212121"));
        tbdHome.setBackgroundAt(3, Color.decode("#212121"));

        tbdHome.setForegroundAt(0, Color.decode("#ffffff"));
        tbdHome.setForegroundAt(1, Color.decode("#ffffff"));
        tbdHome.setForegroundAt(2, Color.decode("#ffffff"));
        tbdHome.setForegroundAt(3, Color.decode("#ffffff"));


        String contraSantiago = "1324";
        String contraGaston = "";
        myDatabase = new Database("localhost", "library", "root", contraGaston);

        // region Buttons & Mnemonics
        tbdHome.setMnemonicAt(0, KeyEvent.VK_1);
        tbdHome.setMnemonicAt(1, KeyEvent.VK_2);
        tbdHome.setMnemonicAt(2, KeyEvent.VK_3);
        tbdHome.setMnemonicAt(3, KeyEvent.VK_4);

        btnBuscar.setMnemonic(KeyEvent.VK_B);
        btnAgregar.setMnemonic(KeyEvent.VK_A);
        btnActualizar.setMnemonic(KeyEvent.VK_A);
        btnActualizarTabla.setMnemonic(KeyEvent.VK_T);
        btnEliminar.setMnemonic(KeyEvent.VK_E);

        btnBuscar.addActionListener(this);
        btnAgregar.addActionListener(this);
        btnActualizar.addActionListener(this);
        btnEliminar.addActionListener(this);
        btnActualizarTabla.addActionListener(this);

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
        dtmLibros.addColumn("Stock");
        dtmLibros.addColumn("Fecha Salida");
        table1.setModel(dtmLibros);

        updateTable();

        listCategory = new JList();
        listCategoryModel = new DefaultListModel();
        listCategory.setModel(listCategoryModel);

        listCategoryModel.addElement("Bebes");
        listCategoryModel.addElement("Niños");
        listCategoryModel.addElement("Jovenes");
        listCategoryModel.addElement("Jovenes Adultos");
        listCategoryModel.addElement("Adultos");
        listCategoryModel.addElement("Adultos XXX +18");



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

        //Color boton
        btnBuscar.setBackground(Color.decode("#f57f17"));
        btnBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnBuscar.setBackground(Color.decode("#ffb04c"));
                btnBuscar.setForeground(Color.decode("#000000"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnBuscar.setBackground(Color.decode("#f57f17"));
                btnBuscar.setForeground(Color.decode("#000000"));
            }
        });


        //Color boton Eliminar
        btnEliminar.setBackground(Color.decode("#dd2c00"));
        btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnEliminar.setBackground(Color.decode("#ff6434"));
                btnEliminar.setForeground(Color.decode("#000000"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnEliminar.setBackground(Color.decode("#dd2c00"));
                btnEliminar.setForeground(Color.decode("#000000"));
            }
        });

        //Color boton acualizar
        btnActualizar.setBackground(Color.decode("#f57f17"));
        btnActualizar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnActualizar.setBackground(Color.decode("#ffb04c"));
                btnActualizar.setForeground(Color.decode("#000000"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnActualizar.setBackground(Color.decode("#f57f17"));
                btnActualizar.setForeground(Color.decode("#000000"));
            }
        });

        //Color boton agregar
        btnAgregar.setBackground(Color.decode("#f57f17"));
        btnAgregar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnAgregar.setBackground(Color.decode("#ffb04c"));
                btnAgregar.setForeground(Color.decode("#000000"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnAgregar.setBackground(Color.decode("#f57f17"));
                btnAgregar.setForeground(Color.decode("#000000"));
            }
        });


        //Color pnlGraficador
        pnlGraficador.setBackground(Color.decode("#ffb04c"));
        btnGraficar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pnlGraficador.setOpaque(true);
                XYSeries oSeries = new XYSeries("Stock de libros");

                int semana1 = Integer.parseInt(txtSemana1.getText());
                int semana2 = Integer.parseInt(txtSemana2.getText());
                int semana3 = Integer.parseInt(txtSemana3.getText());
                int semana4 = Integer.parseInt(txtSemana4.getText());

                oSeries.add(1, semana1);
                oSeries.add(2, semana2);
                oSeries.add(3, semana3);
                oSeries.add(4, semana4);

                XYSeriesCollection oDataset = new XYSeriesCollection();
                oDataset.addSeries(oSeries);

                JFreeChart oChart = ChartFactory.createXYLineChart("Venta de libros", "Semanas", "Cantidad", oDataset, PlotOrientation.VERTICAL, true, false, false);

                ChartPanel oPanel = new ChartPanel(oChart);
                pnlGraficador.setLayout(new java.awt.BorderLayout());
                pnlGraficador.add(oPanel);

            }
        });
        //Color boton graficar
        btnGraficar.setBackground(Color.decode("#f57f17"));
        btnGraficar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnGraficar.setBackground(Color.decode("#ffb04c"));
                btnGraficar.setForeground(Color.decode("#000000"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnGraficar.setBackground(Color.decode("#f57f17"));
                btnGraficar.setForeground(Color.decode("#000000"));
            }
        });

        //Color boton Actualizar
        btnActualizarTabla.setBackground(Color.decode("#f57f17"));
        btnActualizarTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnGraficar.setBackground(Color.decode("#ffb04c"));
                btnGraficar.setForeground(Color.decode("#000000"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnGraficar.setBackground(Color.decode("#f57f17"));
                btnGraficar.setForeground(Color.decode("#000000"));
            }
        });

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                BookDAO bookDAO = new BookDAO(myDatabase);
                try {
                    List<Book> bookList = bookDAO.getByLike(txtBuscar.getText());
                    if ( bookList != null) {
                        for (int i = dtmLibros.getRowCount(); i > 0; i--) {
                            dtmLibros.removeRow(i - 1);
                        }

                        for (Book book : bookList) {
                            dtmLibros.addRow(new Object[] {
                                    book.getIsbn(),
                                    book.getTitle(),
                                    book.getDescription(),
                                    book.getPrice(),
                                    book.getAuthor(),
                                    book.getStock(),
                                    book.getRelease_date()
                            });
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public void updateTable() {

        BookDAO bookDAO = new BookDAO(myDatabase);
        List<Book> books = null;
        try {
            books = bookDAO.getAll();

            if ( books != null) {
                for (int i = dtmLibros.getRowCount(); i > 0; i--) {
                    dtmLibros.removeRow(i - 1);
                }

                for (Book book : books) {
                    dtmLibros.addRow(new Object[] {
                            book.getIsbn(),
                            book.getTitle(),
                            book.getDescription(),
                            book.getPrice(),
                            book.getAuthor(),
                            book.getStock(),
                            book.getRelease_date()
                    });
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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


                Book book = new Book();
                book.setTitle(titulo);
                book.setAuthor(autor);
                book.setDescription(descripcion);
                book.setPrice(precio);
                book.setStock(stock);

                BookDAO bookDAO = new BookDAO(myDatabase);

                bookDAO.add(book);

                // print result in table
                updateTable();

                // reset form
                txtNombreLibro.requestFocus();

                JOptionPane.showMessageDialog(this, "Libro registrado exitosamente", "Exito!", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Palabras en Stock o en precio", "Error", JOptionPane.ERROR_MESSAGE);
                ex.getStackTrace();
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Error al Registrar el libro", "Error", JOptionPane.ERROR_MESSAGE);
                throwables.getStackTrace();
            }


        }

        if ( e.getSource() == btnActualizar) {
            Book book = new Book (
                    Integer.parseInt(String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 0))),
                    String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 1)),
                    String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 2)),
                    String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 3)),
                    Integer.parseInt(String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 4))),
                    String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 5)),
                    Integer.parseInt(String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 6))),
                    Date.valueOf(String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 7)))
            );

            try {
                new UpdateBook(book);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }

        if (e.getSource() == btnEliminar) {
            String uuid = String.valueOf(dtmLibros.getValueAt(table1.getSelectedRow(), 0));
            int option = JOptionPane.showConfirmDialog(this, "Está seguro,", "Eliminar libro", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                BookDAO bookDAO = new BookDAO(myDatabase);
                try {
                    bookDAO.delete(uuid);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            updateTable();
        }

        if ( e.getSource() == btnActualizarTabla ) {
            updateTable();
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
