<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\PasswordResetType;
use App\Form\PasswordType;
use App\Form\ResetPassType;
use App\Repository\UserRepository;
use App\Service\generateToken;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

class SecurityController extends AbstractController
{
    /**
     * @Route("/login", name="app_login")
     */
    public function login(AuthenticationUtils $authenticationUtils): Response
    {
        // if ($this->getUser()) {
        //     return $this->redirectToRoute('target_path');
        // }

        // get the login error if there is one
        $error = $authenticationUtils->getLastAuthenticationError();

        // last username entered by the user
        $lastUsername = $authenticationUtils->getLastUsername();

        return $this->render('security/login.html.twig', ['last_username' => $lastUsername, 'error' => $error]);
    }

    /**
     * @Route("/logout", name="app_logout")
     */
    public function logout()
    {
        //throw new \LogicException('This method can be blank - it will be intercepted by the logout key on your firewall.');
    }

    /**
     * @Route("/activations/{token}", name="activations" )
     */
    public function activation($token,UserRepository $users)
    {
        // On recherche si un utilisateur avec ce token existe dans la base de données
        $user = $users->findOneBy(['activation_token' => $token]);
        // Si aucun utilisateur n'est associé à ce token
        if(!$user){
            // On renvoie une erreur 404
            throw $this->createNotFoundException('Cet utilisateur n\'existe pas');
        }
        $user->setValidation(true);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();
        // On génère un message
        $this->addFlash('message', 'Utilisateur activé avec succès');
        // On retourne à l'accueil
        return $this->redirectToRoute('app_login');
    }

    /**
     * @Route("/passwordrest", name="passwordrest")
     */
    public function passwordrest(){

        return $this->render('security/forget.html.twig');
    }

    /**
     * @Route("/sendtomail", name="sendtomail")
     */
    public function sendtomail(Request $request,MailerInterface $mailer,UserRepository $users){

        $form=$this->createForm(ResetPassType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $email = $form->getData()['email'];
            $user = $users->findOneBy(['email'=>$email]) ;
           if($user)
           {
            $token = $user->getActivationToken() ;
            $emailSend = (new Email())
                ->from('hello@example.com')
                ->to($form['email']->getData())
                //->cc('cc@example.com')
                //->bcc('bcc@example.com')
                //->replyTo('fabien@example.com')
                //->priority(Email::PRIORITY_HIGH)
                ->subject('Time for Symfony Mailer!')
                ->text('Sending emails is fun again!')
                ->html("<p>il faut confirmer votre compte <a href=http://127.0.0.1:8000/forgetpassword/$token >verfier</a></p>");
            $mailer->send($emailSend);
            $this->addFlash("success","the mail have been sent ! ♥ ");
            return $this->redirectToRoute('app_login');
           }
           $this->addFlash("error","user is not found !! :(");
        }


        return $this->render('security/forget.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/forgetpassword/{token}", name="forgetpassword")
     */
    public function forgetpassword($token,Request $request,UserPasswordEncoderInterface $encoder){
        $form=$this->createForm(PasswordResetType::class);
        $form->handleRequest($request);
        $user = $this->getDoctrine()->getRepository(User::class)->findOneBy(['activation_token'=>$token]);
        if($form->isSubmitted() && $form->isValid() && $user){
            $this->addFlash('success',"password changed with success ! ♥ ");
            $user->setPassword($encoder->encodePassword($user,$form->getData()['password']));
            $this->getDoctrine()->getManager()->flush();
            return $this->redirectToRoute('app_login');
        }
        return $this->render("security/resetPassword.html.twig",[
            'form' => $form->createView(),
        ]);
    }
}
