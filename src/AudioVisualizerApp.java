import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.*;
class MyThread extends Thread {
    @Override
    public void run() {
// создание потока
    }
}
public class AudioVisualizerApp {

    MyThread thread = new MyThread();//crearea thredului 1
    MyThread thread1 = new MyThread();//2
    MyThread thread2 = new MyThread();//3
    //despre utilizarea thredurilor in cerintele de evaluare nu este spus nimic
    private Clip[] clips = new Clip[3];  // Массив для звуковых клипов
  //  private boolean[] isPlaying = {false, false, false}; // Статус для звуков (включен/выключен)

    private Frame frame;  // Окно для отображения графики
    // Конструктор, c инициализацией всего
    public AudioVisualizerApp() {
        loadSounds();
        frame = new Frame("ЭТО ИНТЕРФЕЙС");
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose(); // Закрытие окна
                System.exit(0);  // Завершение приложения
            }
        });
        startTimers();
    }

    // Загружаем звуки
    private void loadSounds() {
            loadSound(2, "sound.wav");
        loadSound(0, "aiaiai.wav");
        loadSound(1, "ababa.wav");

    }

    // Загружаем звуковой файл
    private void loadSound(int index, String filename) {
        try {
            File soundFile = new File(filename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clips[index] = AudioSystem.getClip();
            clips[index].open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Запуск таймеров для каждого звука
    private void startTimers() {
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        Timer timer3 = new Timer();

        // Таймер для первого звука (каждую секунду)
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                playSound(0);  // Включаем/выключаем первый звук
            }
        }, 0, 2000);  // Таймер будет срабатывать каждые 2 секунды

        // Таймер для второго звука (через 5 секунд)
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                playSound(1);  // Включаем/выключаем второй звук
            }
        }, 200);  // Таймер будет срабатывать через 5 секунд

        // Таймер для третьего звука с условием: срабатывает только если текущие минуты чётные
        timer3.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Получаем текущее время
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                int minutes = calendar.get(java.util.Calendar.MINUTE);

                // Проверяем, чётные ли минуты
                if (minutes % 2 == 0) {
                    playSound(2);  // Включаем/выключаем третий звук, если минуты чётные
                }
            }
        }, 3000, 2000);  // Таймер начнёт через 3 секунды и будет повторяться каждые 2 секунды
    }

    // Метод для воспроизведения звука
    private void playSound(int index) {
        if (clips[index] != null && !clips[index].isRunning()) {
            clips[index].setFramePosition(0); // Перемещаем курсор в начало
            clips[index].start(); // Воспроизводим звук
        }
    }

    // Запуск приложения
    public static void main(String[] args) {
        new AudioVisualizerApp();
    }
}
