package org.tts.tickettrackermvn.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tts.tickettrackermvn.entities.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Query(value = "SELECT * FROM ticket ti WHERE ti.title LIKE %?1% OR ti.short_description LIKE %?1%", nativeQuery = true)
    List<Ticket> findByTitleOrShortDescription(String query);
}