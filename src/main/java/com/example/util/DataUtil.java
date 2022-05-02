package com.example.util;

public class DataUtil {
//    @Transactional(rollbackFor = Exception.class)
//    public int addFreshStudentsNew(List<FreshStudentAndStudentModel> list, String schoolNo) {
//        if (list == null || list.isEmpty()) {
//            return 0;
//        }
//        List<StudentEntity> studentEntityList = new LinkedList<>();
//        List<EnrollStudentEntity> enrollStudentEntityList = new LinkedList<>();
//        List<AllusersEntity> allusersEntityList = new LinkedList<>();
//
//        list.forEach(freshStudentAndStudentModel -> {
//            EnrollStudentEntity enrollStudentEntity = new EnrollStudentEntity();
//            StudentEntity studentEntity = new StudentEntity();
//            BeanUtils.copyProperties(freshStudentAndStudentModel, studentEntity);
//            BeanUtils.copyProperties(freshStudentAndStudentModel, enrollStudentEntity);
//            String operator = TenancyContext.UserID.get();
//            String studentId = BaseUuidUtils.base58Uuid();
//            enrollStudentEntity.setId(BaseUuidUtils.base58Uuid());
//            enrollStudentEntity.setStudentId(studentId);
//            enrollStudentEntity.setIdentityCardId(freshStudentAndStudentModel.getIdCard());
//            enrollStudentEntity.setOperator(operator);
//            studentEntity.setId(studentId);
//            studentEntity.setIdentityCardId(freshStudentAndStudentModel.getIdCard());
//            studentEntity.setOperator(operator);
//            studentEntityList.add(studentEntity);
//            enrollStudentEntityList.add(enrollStudentEntity);
//
//            AllusersEntity allusersEntity = new AllusersEntity();
//            allusersEntity.setId(enrollStudentEntity.getId());
//            allusersEntity.setUserCode(enrollStudentEntity.getNemtCode());
//            allusersEntity.setUserName(enrollStudentEntity.getName());
//            allusersEntity.setSchoolNo(schoolNo);
//            allusersEntity.setTelNum(enrollStudentEntity.getTelNum());
//            allusersEntity.setPassword(enrollStudentEntity.getNemtCode());  //密码设置为考生号
//            allusersEntityList.add(allusersEntity);
//        });
//
//
//        int nThreads = 50;
//
//        int size = enrollStudentEntityList.size();
//        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
//        List<Future<Integer>> futures = new ArrayList<Future<Integer>>(nThreads);
//
//        for (int i = 0; i < nThreads; i++) {
//            final List<EnrollStudentEntity> EnrollStudentEntityImputList = enrollStudentEntityList.subList(size / nThreads * i, size / nThreads * (i + 1));
//            final List<StudentEntity> studentEntityImportList = studentEntityList.subList(size / nThreads * i, size / nThreads * (i + 1));
//            final List<AllusersEntity> allusersEntityImportList = allusersEntityList.subList(size / nThreads * i, size / nThreads * (i + 1));
//
//            Callable<Integer> task1 = () -> {
//                studentSave.saveStudent(EnrollStudentEntityImputList,studentEntityImportList,allusersEntityImportList);
//                return 1;
//            };
//            futures.add(executorService.submit(task1));
//        }
//        executorService.shutdown();
//        if (!futures.isEmpty() && futures != null) {
//            return 10;
//        }
//        return -10;
//    }
}
