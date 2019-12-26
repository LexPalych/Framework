package com.github.lexpalych.allure.webdriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

final class WebDriverFactory {
    private static final Logger LOGGER = LogManager.getLogger(WebDriverFactory.class);
    private static final OperationSystem system = OperationSystem.getSystemType();

    /**
     * Получение инстанса WebDriver по параметрам
     * @param browserName - имя браузера, допустимы ie, firefox, chrome, html, opera
     * @param timeToWait - время неявного ожидания в секундах
     * @param isHeadless - безголовый режим, поддерживается в firefox и chrome
     * @return - интанс вебдрайвера с заданными параметрами
     */
    static WebDriver getWebDriver(final String browserName, final int timeToWait, final boolean isHeadless) {
        LOGGER.info("Инициализация браузера " + browserName);

        final WebDriver webDriver;

        if (browserName.equalsIgnoreCase("chrome")) {
            webDriver = getChromeDriver(isHeadless);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            webDriver = getFirefoxDriver(isHeadless);
        } else if (browserName.equalsIgnoreCase("opera")) {
            webDriver = getOperaDriver(isHeadless);
        } else if (browserName.equalsIgnoreCase("ie")) {
            webDriver = getExplorerDriver();
        } else {
            throw new InvalidBrowserNameException("Браузер " + browserName + " не поддерживается");
        }

        final Dimension dimension = new Dimension(1920, 1080);
        webDriver.manage().window().setSize(dimension);
        webDriver.manage().deleteAllCookies();
        webDriver.manage().timeouts().implicitlyWait(timeToWait, TimeUnit.SECONDS);

        LOGGER.info("Инициализирован драйвер " + webDriver.getClass().getSimpleName());

        return webDriver;
    }

    /**
     * Настраивает для запуска браузер Firefox
     * @param isHeadless - параметр, отвечающий за безинтерфейсный прогон тестов
     * @return - возвращает готовый к работе браузер
     */
    private static WebDriver getFirefoxDriver(final boolean isHeadless) {
        prepareDrivers("geckodriver");

        final FirefoxOptions firefoxOptions = new FirefoxOptions();

        firefoxOptions.setHeadless(isHeadless);
        firefoxOptions.addArguments("--width=1920", "--height=1080");
        firefoxOptions.setCapability("marionette", true);

        return new FirefoxDriver(firefoxOptions);
    }

    /**
     * Настраивает для запуска браузер InternetExplorer
     * @return - возвращает готовый к работе браузер
     */
    private static WebDriver getExplorerDriver() {
        prepareDrivers("IEDriverServer");

        final InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        internetExplorerOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

        return new InternetExplorerDriver(internetExplorerOptions);
    }

    /**
     * Настраивает для запуска браузер Chrome
     * @param isHeadless - параметр, отвечающий за безинтерфейсный прогон тестов
     * @return - возвращает готовый к работе браузер
     */
    private static WebDriver getChromeDriver(final boolean isHeadless) {
        prepareDrivers("chromedriver");

        final ChromeOptions chromeOptions = new ChromeOptions();

        if (isHeadless) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("disable-gpu");
        }

        chromeOptions.addArguments("--test-type");
        chromeOptions.addArguments("--ignore-certificate-error");
        chromeOptions.addArguments("window-size=1920,1080");
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        chromeOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

        return new ChromeDriver(chromeOptions);
    }

    /**
     * Настраивает для запуска браузер Opera
     * @param isHeadless - параметр, отвечающий за безинтерфейсный прогон тестов
     * @return - возвращает готовый к работе браузер
     */
    private static WebDriver getOperaDriver(final boolean isHeadless) {
        prepareDrivers("operadriver");

        final OperaOptions operaOptions = new OperaOptions();

        operaOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        operaOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

        return new OperaDriver(operaOptions);
    }

    /**
     * Какая-то пока непонятна я фигня
     * @param browserName
     * @throws IOException
     * @throws InterruptedException
     */
    private static void killDriverAndBrowser(final String browserName) throws IOException, InterruptedException {
        LOGGER.info("Завершение процессов драйвера и браузера " + browserName);

        if (browserName.equalsIgnoreCase("chrome")) {
            Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f").waitFor();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            Runtime.getRuntime().exec("taskkill /im geckodriver.exe /f").waitFor();
        } else if (browserName.equalsIgnoreCase("ie")) {
            Runtime.getRuntime().exec("taskkill /im iedriverserver.exe /f").waitFor();
        }
    }

    /**
     * Сетит драйвер браузера
     * @param driverExecutableName - имя .exe файла драйвера
     */
    private static void prepareDrivers(final String driverExecutableName) {
        LOGGER.info("Операционная система " + system);
        if (system == OperationSystem.WINDOWS) {
            final var path = getResourcePath("drivers/windows/" + driverExecutableName + ".exe");
            setExecutableProperty(driverExecutableName, path);

        } else if (system == OperationSystem.LINUX) {
            final var path = getResourcePath("drivers/linux/" + driverExecutableName);
            setExecutableProperty(driverExecutableName, path);
            makeExecutableOnLinux(path);
        }
    }

    /**
     * Какая-то прикалюха для тестов на линуксе
     * @param path
     */
    private static void makeExecutableOnLinux(final String path) {
        try {
            Runtime.getRuntime().exec("chmod +x " + path);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Составляет полный путь до файла
     * @param resourceName - путь до файла в папке resources
     * @return - возвращает полный путь до файла
     */
    private static String getResourcePath(final String resourceName) {
//        final ClassLoader classLoader = WebDriverFactory.class.getClassLoader();
//        return classLoader.getResource(resourceName).getPath();
        return System.getProperty("user.dir") + "/build/resources/main/" + resourceName;
    }

    /**
     * Устанавливает определённое свойства драйвера в зависимости от браузера
     * @param driverExecutableName - свойство
     * @param path - путь, по которому ищется драйвер
     */
    private static void setExecutableProperty(final String driverExecutableName, final String path) {
        switch (driverExecutableName) {
            case "chromedriver":
                System.setProperty("webdriver.chrome.driver", path);
                break;

            case "geckodriver":
                System.setProperty("webdriver.gecko.driver", path);
                break;

            case "IEDriverServer":
                System.setProperty("webdriver.ie.driver", path);
                break;

            case "operadriver":
                System.setProperty("webdriver.operadriver.driver", path);
                break;
        }
    }

    private static class InvalidBrowserNameException extends RuntimeException {
        InvalidBrowserNameException(final String message) {
            super(message);
        }
    }

    private enum OperationSystem {
        MAC_OS("mac", "darwin"),
        WINDOWS("win"),
        LINUX("nux"),
        OTHER("generic");

        private static OperationSystem detecteSystem;
        private final String[] names;

        OperationSystem(final String... names) {
            this.names = names;
        }

        private boolean match(final String name) {
            for (String osName : this.names) {
                if (name.contains(osName)) {
                    return true;
                }
            }
            return false;
        }

        public static OperationSystem getSystemType() {
            if (detecteSystem == null) {
                detecteSystem = getOperationSystemType(System.getProperty("os.name", OTHER.names[0]).toLowerCase());
            }

            return detecteSystem;
        }

        private static OperationSystem getOperationSystemType(final String name) {
            for (final OperationSystem osType : values()) {
                if (osType.match(name)) {
                    return osType;
                }
            }

            return OTHER;
        }
    }
}
