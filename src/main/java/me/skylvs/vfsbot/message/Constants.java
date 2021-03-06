package me.skylvs.vfsbot.message;

public class Constants {

    public static final String GREETINGS_MESSAGE = "Доброго утра/дня/вечера! Этот бот создан для того, чтобы помочь вам в отслеживании статуса визового заявления в консульстве Республики Польша при подаче через VFS Global \uD83D\uDE0A\uD83C\uDDF5\uD83C\uDDF1\n" +
            "\n" +
            "Бот будет функционировать при условии, что вы подключили услугу отслеживания статуса заявления непосредственно при подаче в визовом центре. После введения необходимых данных, бот будет отправлять уведомления при обновлении статуса визового заявления. По любым вопросам насчет бота, обращайтесь @tryyourselftwice\n" +
            "\n" +
            "Для начала использования, введите пожалуйста Reference Number, предоставленный после подачи заявления в формате:\n" +
            "CITY/100322/0001/01";

    public static final String INVALID_REFERENCE_NUMBER_FORMAT = "Неправильный формат Reference number. Он должен быть в формате CITY/100322/0001/01";

    public static final String BIRTHDATE_WAITING = "Теперь введите, пожалуйста, дату рождения человека, для которого подано заявление о визе в формате DD/MM/YYYY - например 07/09/1990";

    public static final String INVALID_BIRTHDATE_FORMAT = "Неверный формат даты рождения. Формат - DD/MM/YYYY";

    public static final String READY_TO_GO = "Спасибо! Теперь вам будут приходить уведомления об обновлении статуса визового заявления.";

    public static final String INVALID_DATA = "\uD83E\uDD28 К сожалению, при проверке статуса вашего визового заявления произошла ошибка. Вот ответ VFS Global: %s\n" +
            "\n" +
            "Проверьте пожалуйста правильность введенных прежде данных:\n" +
            "Reference number: %s\n" +
            "Дата рождения: %s\n" +
            "\n" +
            "Так же напоминаем, что бот будет функционировать только если вы приобретали услугу отслеживания статуса заявления непосредственно при его подаче в VFS Global. Советуем перейти по [ссылке](https://www.vfsvisaservicesrussia.com/poland-Russia-tracking_new/trackingParam.aspx?P=ri7FHohe3VirNKmyLaRu36t9/pEItw3gfYXFtDFlxVY=) и попробовать отследить статус заявления вручную. Если вы желаете дальше пользоваться ботом и получать уведомления, введите пожалуйста исправленный Reference Number в следующем сообщении \uD83D\uDE0A";

    public static final String CHECK_STATUS = "Узнать статус визового заявления";
}
