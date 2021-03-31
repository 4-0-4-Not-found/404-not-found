<?php

namespace App\Entity;

use App\Repository\CategoryRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=CategoryRepository::class)
 */
class Category
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $nbr_etoile;

    /**
     * @ORM\OneToMany(targetEntity=Hotel::class, mappedBy="categories",orphanRemoval=true)
     */
    private $hotels;
    public function __construct()
    {
        $this->hotels = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNbrEtoile(): ?string
    {
        return $this->nbr_etoile;
    }

    public function setNbrEtoile(string $nbr_etoile): self
    {
        $this->nbr_etoile = $nbr_etoile;

        return $this;
    }

    /**
     * @return Collection|Hotel[]
     */
    public function getHotels(): Collection
    {
        return $this->hotels;
    }

    public function addHotel(Hotel $hotel): self
    {
        if (!$this->hotels->contains($hotel)) {
            $this->hotels[] = $hotel;
            $hotel->setCategories($this);
        }

        return $this;
    }

    public function removeHotel(Hotel $hotel): self
    {
        if ($this->hotels->removeElement($hotel)) {
            // set the owning side to null (unless already changed)
            if ($hotel->getCategories() === $this) {
                $hotel->setCategories(null);
            }
        }

        return $this;
    }
    public function __toString(){
        return $this->nbr_etoile;
    }
}
