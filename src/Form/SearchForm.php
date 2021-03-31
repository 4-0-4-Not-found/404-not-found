<?php


namespace App\Form;


use App\Data\SearchData;
use App\Entity\Category;
use App\Entity\Hotel;
use App\Repository\HotelRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\ChoiceList\ArrayChoiceList;
use Symfony\Component\Form\ChoiceList\Loader\CallbackChoiceLoader;
use Symfony\Component\Form\ChoiceList\View\ChoiceListView;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Mapping\Loader\StaticMethodLoader;

class SearchForm extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('q',TextType::class,[
                'label'=>false,
                'required'=>false,
                'attr'=>[
                    'placeholder'=>'Rechercher'
                ]
            ])
            ->add('location',ChoiceType::class,[
                'choices'=> [
                    'Tunis'=>'tunis',
                    'Sousse'=>'sousse',
                    'hammamet'=>'hammamet',
                    'Tozeur'=>'Tozeur'
                ],
                'placeholder'=>'',
                'required'=>false


            ])

            ->add('categories', EntityType::class, [
                'label'=>false,
                'required'=>false,
                'class'=>Category::class,
                'expanded'=>true,
                'multiple'=>false
            ])
            ->add('min',NumberType::class, [
                'label'=>false,
                'required'=>false,
                'attr'=>[
                    'placeholder'=>'prix min'
                ]
            ])
            ->add('max',NumberType::class,[
                'label'=>false,
                'required'=>false,
                'attr'=>[
                    'placeholder'=>'prix max'
                ]
            ])
            ->add('promotion',CheckboxType::class ,[
                'label'=>'En promotion',
                'required'=>false,
            ])
            ->add('prix', CheckboxType::class, [
                'label_attr' => ['class' => 'switch-custom'],
                 'required'=>false
//

            ])


        ;
    }

    public function configureOptions(OptionsResolver $resolver)
{
    $resolver->setDefaults([
        'data_class'=>SearchData::class,
        'method'=>'GET',
        'csrf_protection'=>false
    ]);
}
public function getBlockPrefix()
{
    return '';
}
}