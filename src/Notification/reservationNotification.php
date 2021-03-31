<?php


namespace App\Notification;


use App\Entity\ReservHotel;
use Swift_Mailer;
use Twig\Environment;

class reservationNotification
{
    /**
     * @var Swift_Mailer
     */
    private $mailer;
    /**
     * @var Environment
     */
    private $renderer;

    public function _construct(Swift_Mailer $mailer, Environment $renderer){
         $this->mailer=$mailer;
         $this->renderer=$renderer;
    }

  public function notify(ReservHotel $ReservHotel){
    $message=( new \Swift_Message('Reservation de '.$ReservHotel->getHotel()->getNom()))
        ->setFrom('noreply@agence.tn')
        ->setTo('reservation@agence.tn')
        ->setReplyTo($ReservHotel->getEmail())
        ->setBody($this->renderer->render('emails/reservation.html.twig',[
            'ReservHotel'=>$ReservHotel
        ]),'text/html');
         $this->mailer->send($message);
  }
}