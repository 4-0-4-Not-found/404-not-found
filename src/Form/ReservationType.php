<?php

namespace App\Form;

use App\Entity\Activite;
use App\Entity\Hotel;
use App\Entity\Reservation;
use App\Entity\User;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReservationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('arrival')
            ->add('leaving')
            ->add('num_people')
            ->add('num_rooms')
            ->add('hotel',EntityType::class,[
                'class' => Hotel::class,
                'choice_label' => 'nom',
            ])
            ->add('client',EntityType::class,[
                'class' => User::class,
                'choice_label' => 'nom',
            ])
            ->add('activity', EntityType::class,[
                'class' => Activite::class,
                'choice_label' => 'nom',
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Reservation::class,
        ]);
    }
}
