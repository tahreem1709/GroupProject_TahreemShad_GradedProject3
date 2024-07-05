package org.tts.tickettrackermvn.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.tts.tickettrackermvn.entities.Ticket;
import org.tts.tickettrackermvn.repositories.TicketRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/")
    public String getTicketList(Model model){
        model.addAttribute("tickets", ticketRepository.findAll());
        return "index";
    }

    @GetMapping("/view-ticket/{id}")
    public String viewTicket(@PathVariable("id") long id, Model model){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ticket id: " + id));

        model.addAttribute("ticket", ticket);
        return "view-ticket";
    }

    @GetMapping("/new-ticket")
    public String createTicket(Ticket ticket){
        return "create-ticket";
    }

    @PostMapping("/create")
    public String createTicket(@Valid Ticket ticket, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "create-ticket";
        }
        ticket.setNew(true);
        ticketRepository.save(ticket);
        return "redirect:/admin/tickets/";
    }

    @GetMapping("/edit/{id}")
    public String editTicket(@PathVariable("id") long id, Model model){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ticket id: " + id));

        model.addAttribute("ticket", ticket);
        return "edit-ticket";
    }

    @PostMapping("/update/{id}")
    public String updateTicket(@PathVariable("id") long id, @Valid Ticket ticket, BindingResult result, Model model){
        if(result.hasErrors()){
            ticket.setId(id);
            return "edit-ticket";
        }
        ticket.setNew(false);
        ticketRepository.save(ticket);
        return "redirect:/admin/tickets/";
    }

    @GetMapping("/search")
    public String searchTicket(Model model, @RequestParam("query") Optional<String> query){
        if(query.isEmpty()){
            return "redirect:/admin/tickets/";
        }
        List<Ticket> ticket = ticketRepository.findByTitleOrShortDescription(query.get());
        model.addAttribute("tickets", ticket);
        model.addAttribute("query", query.get());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable("id") long id, Model model){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ticket id: " + id));
        ticketRepository.delete(ticket);
        model.addAttribute("tickets", ticketRepository.findAll());
        return "redirect:/admin/tickets/";
    }
}