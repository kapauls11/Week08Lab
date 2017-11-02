/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import domainmodel.Note;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author 698437
 * @version October 29,2017
 */
public class NoteDB {
    
     private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date newDate = new java.sql.Date(uDate.getTime());
        return newDate;
}
    
     public int insert(Note note) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try{
            trans.begin();
            em.persist(note);
            trans.commit();
            return 1;
        } catch (Exception ex)
        {
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot insert " + note.toString(), ex);
            throw new NotesDBException("Error inserting note");
        } finally{
            em.close();
        }
    }
     
     public int update(Note note) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try{
            trans.begin();
            em.merge(note);
            trans.commit();
            return 1;
        } catch (Exception ex)
        {
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot update " + note.toString(), ex);
            throw new NotesDBException("Error updating note");
        } finally{
            em.close();
        }
    }
     
      public List<Note> getAll() throws NotesDBException {
         EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try{
            List<Note> notes = em.createNamedQuery("Notes.findAll", Note.class).getResultList();
            return notes;
        } catch (Exception ex){
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot read notes", ex);
            throw new NotesDBException("Error getting notes");
        } finally{
            em.close();
        }
     }
      
      /**
     * Get a single note by the note ID.
     *
     * @param noteID The unique noteID.
     * @return A Note object if found, null otherwise.
     * @throws NotesDBException
     */
    public Note get(int noteID) throws NotesDBException {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try{
            Note note = em.find(Note.class, noteID);
            return note;
        } catch (Exception ex)
        {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot read notes", ex);
            throw new NotesDBException("Error getting notes");
        } finally{
            em.close();
      }
    }
    public int delete(Note note) throws NotesDBException {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try{
            trans.begin();
            em.remove(em.merge(note));
            trans.commit();
            return 1;
        } catch (Exception ex){
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot delete " + note.toString(), ex);
            throw new NotesDBException("Error deleting note");
        } finally{
            em.close();
        }
    }
    
     public Note getNote(long noteID){
        Note note = new Note();
        note.setNoteID((int) noteID);
        return note;
    }
}
