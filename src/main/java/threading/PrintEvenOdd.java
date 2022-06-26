package threading;

// Print odd numbers using one thread - oddPrinter and
// even numbers using the other thread - evenPrinter.
// The idea is to use the System.out.println as the printer resources,
// once one thread prints its value (a number which is even/odd), inform the other thread
// waiting to print its value.
// This sample code uses synchronization on the imaginary `printerResource`,
// there will obviously be other flavors to it.
public class PrintEvenOdd {
    static Integer number = 1;

    public static void main(String[] args) {
        Object printerResource = new Object();
        var oddPrinter = new Thread(() -> {
            while (number <= 10) {
                synchronized (printerResource){
                    if (number % 2 != 0 && number <= 10) {
                        System.out.println("Odd Printer - %d".formatted(number));
                        number++;
                        printerResource.notifyAll();
                    }
                }
            }
        });
        var evenPrinter = new Thread(() -> {
            while (number <= 10) {
                synchronized (printerResource) {
                    if (number % 2 == 0 && number <= 10) {
                        System.out.println("Even Printer - %d".formatted(number));
                        number++;
                        printerResource.notifyAll();
                    }
                }
            }
        });
        oddPrinter.start();
        evenPrinter.start();
    }
}
