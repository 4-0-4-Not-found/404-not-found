<?php

namespace App\Entity;

use App\Repository\ReservationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=ReservationRepository::class)
 */
class Reservation
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="date")
     */
    private $arrival;

    /**
     * @ORM\Column(type="date")
     */
    private $leaving;

    /**
     * @ORM\Column(type="integer")
     */
    private $num_people;

    /**
     * @ORM\Column(type="integer")
     */
    private $num_rooms;

    /**
     * @ORM\ManyToMany(targetEntity=Hotel::class, inversedBy="reservations")
     */
    private $hotel;

    /**
     * @ORM\ManyToMany(targetEntity=User::class, inversedBy="reservations")
     */
    private $client;

    /**
     * @ORM\ManyToMany(targetEntity=Activite::class, inversedBy="reservations")
     */
    private $activity;

    public function __construct()
    {
        $this->hotel = new ArrayCollection();
        $this->client = new ArrayCollection();
        $this->activity = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getArrival(): ?\DateTimeInterface
    {
        return $this->arrival;
    }

    public function setArrival(\DateTimeInterface $arrival): self
    {
        $this->arrival = $arrival;

        return $this;
    }

    public function getLeaving(): ?\DateTimeInterface
    {
        return $this->leaving;
    }

    public function setLeaving(\DateTimeInterface $leaving): self
    {
        $this->leaving = $leaving;

        return $this;
    }

    public function getNumPeople(): ?int
    {
        return $this->num_people;
    }

    public function setNumPeople(int $num_people): self
    {
        $this->num_people = $num_people;

        return $this;
    }

    public function getNumRooms(): ?int
    {
        return $this->num_rooms;
    }

    public function setNumRooms(int $num_rooms): self
    {
        $this->num_rooms = $num_rooms;

        return $this;
    }

    /**
     * @return Collection|Hotel[]
     */
    public function getHotel(): Collection
    {
        return $this->hotel;
    }

    public function addHotel(Hotel $hotel): self
    {
        if (!$this->hotel->contains($hotel)) {
            $this->hotel[] = $hotel;
        }

        return $this;
    }

    public function removeHotel(Hotel $hotel): self
    {
        $this->hotel->removeElement($hotel);

        return $this;
    }

    /**
     * @return Collection|User[]
     */
    public function getClient(): Collection
    {
        return $this->client;
    }

    public function addClient(User $client): self
    {
        if (!$this->client->contains($client)) {
            $this->client[] = $client;
        }

        return $this;
    }

    public function removeClient(User $client): self
    {
        $this->client->removeElement($client);

        return $this;
    }

    /**
     * @return Collection|Activite[]
     */
    public function getActivity(): Collection
    {
        return $this->activity;
    }

    public function addActivity(Activite $activity): self
    {
        if (!$this->activity->contains($activity)) {
            $this->activity[] = $activity;
        }

        return $this;
    }

    public function removeActivity(Activite $activity): self
    {
        $this->activity->removeElement($activity);

        return $this;
    }
}
