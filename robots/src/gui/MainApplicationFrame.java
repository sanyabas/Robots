package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame implements SerializableFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private static final String fileName = "frame.state";

    public MainApplicationFrame()
    {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);


        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                String[] buttons = new String[]{"Да", "Нет"};
                int promptResult = JOptionPane.showOptionDialog(
                        null,
                        "Уверены, что хотите выйти?",
                        "Выход",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        buttons,
                        buttons[1]);
                if (promptResult == JOptionPane.YES_OPTION)
                    onExit();
            }

            @Override
            public void windowOpened(WindowEvent e)
            {
                super.windowOpened(e);
                onOpen();
            }
        });
    }

    private void onOpen()
    {
        String directory = System.getProperty("user.home");
        File file = new File(directory, fileName);
        if (!file.exists())
            return;
        FrameState state = null;
        try (FileInputStream fileStream = new FileInputStream(file); ObjectInputStream stream = new ObjectInputStream(fileStream))
        {
            state=restore(stream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (state != null)
            setState(state);
        else
        {
            LogWindow logWindow = createLogWindow();
            addWindow(logWindow);

            GameWindow gameWindow = new GameWindow();
            gameWindow.setSize(400, 400);
            addWindow(gameWindow);
        }
    }

    private void setState(FrameState state)
    {

        for (InternalFrameState internalState : state.getFrames())
        {
            if (internalState.name.equals(GameWindow.class.getName()))
            {
                GameWindow window = new GameWindow();
                window.setState(internalState);
                addWindow(window);
            } else if (internalState.name.equals(LogWindow.class.getName()))
            {
                LogWindow window = createLogWindow();
                window.setState(internalState);
                addWindow(window);
            }
        }
    }

    private void onExit()
    {
        saveState();
        System.exit(0);
    }

    private void saveState()
    {
        String directory = System.getProperty("user.home");
        File file = new File(directory, fileName);
        if (!file.exists())
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        try (FileOutputStream fileStream = new FileOutputStream(file); ObjectOutputStream stream = new ObjectOutputStream(fileStream))
        {
            serialize(stream);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = createMenu("Файл", KeyEvent.VK_T, "Файл");
        JMenuItem close = createMenuItem("Выход", KeyEvent.VK_S, (event) ->
        {
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                    new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        fileMenu.add(close);
        menuBar.add(fileMenu);

        JMenu createMenu = createMenu("Создать", KeyEvent.VK_S, "Создать");
        JMenuItem newLogWindow = createMenuItem("Окно лога", KeyEvent.VK_T, (event) ->
        {
            LogWindow window = createLogWindow();
            addWindow(window);
        });
        JMenuItem newGameWindow = createMenuItem("Игровое окно", KeyEvent.VK_T, (event) ->
        {
            GameWindow window = new GameWindow();
            window.setSize(400, 400);
            addWindow(window);
            PositionWindow posWindow = new PositionWindow(window.getVisualizer());
            posWindow.setSize(200,200);
            addWindow(posWindow);
        });
//        JMenuItem newPositionWindow = createMenuItem("Окно координат",KeyEvent.VK_T,(event)->
//        {
//            PositionWindow window = new PositionWindow()
//        })
        createMenu.add(newLogWindow);
        createMenu.add(newGameWindow);
        menuBar.add(createMenu);

        JMenu lookAndFeelMenu = createMenu("Режим отображения", KeyEvent.VK_V, "Управление режимом отображения приложения");
        JMenuItem systemLookAndFeel = createMenuItem("Системная схема", KeyEvent.VK_S, (event) ->
        {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        JMenuItem crossplatformLookAndFeel = createMenuItem("Универсальная схема", KeyEvent.VK_S, (event) ->
        {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);
        lookAndFeelMenu.add(crossplatformLookAndFeel);
        menuBar.add(lookAndFeelMenu);

        JMenu testMenu = createMenu("Тесты", KeyEvent.VK_T, "Тестовые команды");
        JMenuItem addLogMessageItem = createMenuItem("Сообщение в лог", KeyEvent.VK_S, (event) ->
                Logger.debug("Новая строка")
        );
        testMenu.add(addLogMessageItem);
        menuBar.add(testMenu);

        return menuBar;
    }

    private JMenu createMenu(String name, int mnemonic, String description)
    {
        JMenu lookAndFeelMenu = new JMenu(name);
        lookAndFeelMenu.setMnemonic(mnemonic);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                description);
        return lookAndFeelMenu;
    }

    private JMenuItem createMenuItem(String text, int mnemonic, ActionListener listener)
    {
        JMenuItem resultItem = new JMenuItem(text, mnemonic);
        resultItem.addActionListener(listener);
        return resultItem;
    }


    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }

    @Override
    public void serialize(ObjectOutputStream stream) throws IOException
    {
        FrameState state = new FrameState(this);
        stream.writeObject(state);
    }

    @Override
    public FrameState restore(ObjectInputStream stream) throws IOException, ClassNotFoundException
    {
        FrameState state = (FrameState) stream.readObject();
        return state;
    }

    public JInternalFrame[] getAllFrames()
    {
        return desktopPane.getAllFrames();
    }
}
