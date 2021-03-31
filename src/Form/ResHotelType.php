<?php


namespace App\Form;


use App\Entity\ReservHotel;
use Captcha\Bundle\CaptchaBundle\Form\Type\CaptchaType;
use Captcha\Bundle\CaptchaBundle\Validator\Constraints\ValidCaptcha;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ResHotelType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom',TextType::class )
            ->add('prenom', TextType::class)
            ->add('phone',TextType::class)
            ->add('email',TextType::class)
            ->add('DateEntrer',DateType::class,[
                'widget' => 'choice',
                'input'  => 'datetime_immutable',
                'placeholder' => [
        'year' => 'Year', 'month' => 'Month', 'day' => 'Day',
            ]])
            ->add('DateSortie',DateType::class)
            ->add('captchaCode', CaptchaType::class, array(
                'captchaConfig' => 'ReservHotel',
                'constraints' => [
                    new ValidCaptcha([
                        'message' => 'Invalid captcha, please try again',
                    ]),
                ],
            ))
            ;
    }
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => ReservHotel::class
        ]);
    }
}