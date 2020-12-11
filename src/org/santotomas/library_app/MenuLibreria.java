package org.santotomas.library_app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.santotomas.library_app.dao.BookDAO;
import org.santotomas.library_app.dao.CategoryDAO;
import org.santotomas.library_app.dao.Database;
import org.santotomas.library_app.models.Book;
import org.santotomas.library_app.models.Category;
import org.santotomas.library_app.table.models.BookTableModel;
import org.santotomas.library_app.table.render.BookTableRender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MenuLibreria extends JFrame implements ActionListener {

    // region Components
    private JPanel pnlPanel;
    private JTabbedPane tbdHome;
    private JPanel pnlBuscar;
    private JTextField txtBuscar;
    private JCheckBox chkCategoriaAdultos;
    private JCheckBox chkCategoriaJovenesAdultos;
    private JCheckBox chkCategoriaNinos;
    private JCheckBox chkCategoriaJovenes;
    private JCheckBox chkCategoriaAdultosxxx;
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
    private JRadioButton rNino;
    private JRadioButton rJovenes;
    private JRadioButton rJovenesAdultos;
    private JRadioButton rAdultos;
    private JRadioButton rAdultosxxx;
    private JPanel pnlGrafico;
    private JPanel pnlContenidoGrafico;
    private JPanel pnlGraficador;
    private JButton btnGraficar;
    private JButton btnActualizarTabla;
    private JTextField txtIsbn;
    // endregion

    private Database myDatabase;
    private DefaultTableModel dtmLibros;
    private ButtonGroup bgCategorias;

    private CategoryDAO categoryDAO;
    private BookDAO bookDAO;
    private BookTableModel tableModel;


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
        categoryDAO = new CategoryDAO(myDatabase);
        bookDAO = new BookDAO(myDatabase);

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

        // region Radio Buttons Group
        bgCategorias = new ButtonGroup();
        bgCategorias.add(rNino);
        bgCategorias.add(rJovenes);
        bgCategorias.add(rJovenesAdultos);
        bgCategorias.add(rAdultosxxx);
        bgCategorias.add(rAdultos);

        rNino.setActionCommand("Niños");
        rJovenes.setActionCommand("Jovenes");
        rJovenesAdultos.setActionCommand("Jovenes Adultos");
        rAdultos.setActionCommand("Adultos");
        rAdultosxxx.setActionCommand("Adultos +18");
        // endregion

        btnCloseSession.setIcon(new ImageIcon("src/org/santotomas/library_app/img/close_icon.png"));
        btnCloseSession.addActionListener(this);
        // endregion

        /*
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
         */

        tableModel = new BookTableModel(bookDAO.getAll());
        table1.setModel(tableModel);

        tableModel.refreshTable(bookDAO.getAll());


        table1.setDefaultRenderer(Integer.class, new BookTableRender(myDatabase));
        table1.setDefaultRenderer(String.class, new BookTableRender(myDatabase));
        table1.setDefaultRenderer(Integer.class, new BookTableRender(myDatabase));
        table1.setDefaultRenderer(Date.class, new BookTableRender(myDatabase));

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
                pnlGraficador.removeAll();

                pnlGraficador.setOpaque(true);
                DefaultCategoryDataset oDataSet = new DefaultCategoryDataset();

                BookDAO bookDAO = new BookDAO(myDatabase);
                List<Book> books = null;
                List<Integer> calculo = new ArrayList<>();
                List<String> titulos = new ArrayList<>();
                 try {
                    books = bookDAO.getAll();

                     if ( books != null) {
                         for (Book book : books) {
                             titulos.add(book.getTitle());
                         }

                         for (Book book : books) {
                             calculo.add(book.getPrice() * book.getStock());
                         }
                     }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                /*
                for (int formula : calculo) {
                    oDataSet.setValue(calculo.indexOf(formula), String.valueOf(titulos) , String.valueOf(formula));
                }
                 */
                for (int i = 0 ; i < calculo.size(); i++ ){
                    oDataSet.setValue( calculo.get(i) , "Libros" , titulos.get(i) );
                }


                JFreeChart oChart = ChartFactory.createBarChart3D("Proyeccion", "Libros", "Stock * Precio", oDataSet, PlotOrientation.VERTICAL, true, true, false);

                CategoryPlot plot = new CategoryPlot();
                plot.setRangeGridlinePaint(Color.black);

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
                        tableModel.refreshTable(bookList);
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
                tableModel.refreshTable(books);
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

        if ( e.getSource() == btnBuscar ) {
            BookDAO bookDAO = new BookDAO(myDatabase);
            List<Book> books = null;

            String[] chkStrings = new String[] {
                    chkCategoriaAdultos.isSelected() ? "4" : " ",
                    chkCategoriaNinos.isSelected() ? "1" : " ",
                    chkCategoriaAdultosxxx.isSelected() ? "5" : " ",
                    chkCategoriaJovenes.isSelected() ? "2" : " ",
                    chkCategoriaJovenesAdultos.isSelected() ? "3" : " "
            };

            books = bookDAO.getByLike(txtBuscar.getText(), chkStrings);

            if ( books != null ) {
                tableModel.refreshTable(books);
            }
        }

        if ( e.getSource() == btnAgregar) {
            try {
                String isbn = txtIsbn.getText();
                String titulo = txtNombreLibro.getText();
                String autor = txtAutor.getText();
                String descripcion = txtDescripcion.getText();
                int precio = Integer.parseInt(spnPrecio.getValue().toString());
                int stock = Integer.parseInt(spnStock.getValue().toString());
                int categoria = 0;

                switch ( bgCategorias.getSelection().getActionCommand() ) {
                    case "Niños":
                        categoria = 1;
                        break;
                    case "Jovenes":
                        categoria = 2;
                        break;
                    case "Jovenes Adultos":
                        categoria = 3;
                        break;
                    case "Adultos":
                        categoria = 4;
                        break;
                    case "Adultos +18":
                        categoria = 5;
                        break;

                    default: categoria = 0;
                }

                Book book = new Book();
                book.setIsbn(isbn);
                book.setTitle(titulo);
                book.setAuthor(autor);
                book.setDescription(descripcion);
                book.setPrice(precio);
                book.setStock(stock);
                book.setCategoryId(categoria);

                //daoBrand.getById(product.getBrandIdFk()).getName(),

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
            int selectedRow = table1.getSelectedRow();
            if ( selectedRow != -1 ) {
                Book book = new Book(
                        String.valueOf(tableModel.getValueAt(selectedRow, 0)),
                        String.valueOf(tableModel.getValueAt(selectedRow, 1)),
                        String.valueOf(tableModel.getValueAt(selectedRow, 2)),
                        Integer.parseInt(String.valueOf(tableModel.getValueAt(selectedRow, 3))),
                        categoryDAO.getByUUID(String.valueOf(tableModel.getValueAt(selectedRow, 4))).getId(),
                        String.valueOf(tableModel.getValueAt(selectedRow, 5)),
                        Integer.parseInt(String.valueOf(tableModel.getValueAt(selectedRow, 6))),
                        Date.valueOf(String.valueOf(tableModel.getValueAt(selectedRow, 7)))
                );

                try {
                    new UpdateBook(book);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No row selected!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }

        if (e.getSource() == btnEliminar) {
            int selectedRow = table1.getSelectedRow();

            if (selectedRow != -1) {
                String uuid = String.valueOf(tableModel.getValueAt(table1.getSelectedRow(), 0));
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
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No row selected!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }

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
