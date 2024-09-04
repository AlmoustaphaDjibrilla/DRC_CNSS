package ne.cnss.immatriculation.service;

import ne.cnss.immatriculation.model.Notification;
import ne.cnss.immatriculation.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification saveNotification(Notification notification){
        return notificationRepository.save(notification);
    }
}
