/*package shashi.uomtrust.ac.mu.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import shashi.uomtrust.ac.mu.entity.Request;
import shashi.uomtrust.ac.mu.enums.RequestStatus;



@Repository
public class RequestRepositoryImpl implements RequestRepository {

	@PersistenceContext
	protected EntityManager em;
	
	
	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(Iterable<Request> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Request> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Request> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Request> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Request> List<S> findAll(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Request> List<S> findAll(Example<S> arg0, Sort arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Request getOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Request> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Request> S saveAndFlush(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Request> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Request arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Request> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Request findOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Request> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Request> long count(Example<S> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Request> boolean exists(Example<S> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Request> Page<S> findAll(Example<S> arg0, Pageable arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Request> S findOne(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRequestStatustById(Integer request_id, RequestStatus request_status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRequestById(Integer request_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Request getRequestById(Integer request_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Request> getListRequestsByStatus(RequestStatus request_status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Request> getRequestByUserIdAndRequestStatus(Integer account_id, Integer request_status) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Request> getPendingRequestForTaxi() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select * ");
		sb.append("		from request r ");
				sb.append(	"	where r.request_status = 0 ");
				sb.append("	and r.place_from in ( ");
				sb.append("		select a.address ");
				sb.append("		from account a ");
				sb.append( " where a.address = 'Chemin Grenier') ");
				sb.append(" order by r.request_id desc");
		
				Query q = em.createNativeQuery(sb.toString());

		return q.getResultList();
	}	
	
	
	
}
*/